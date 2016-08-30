package coyc.com.friendmusic.d_Parse;

import android.os.Environment;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.WifiDirectManager;

import org.json.JSONException;
import org.json.JSONObject;

import coyc.com.friendmusic.a_UI.Activity.MainActivity;
import coyc.com.friendmusic.a_UI.Activity.MainActivity_old;
import coyc.com.friendmusic.b_Music.LocalMusicInfoManager;
import coyc.com.friendmusic.b_Music.Player;
import coyc.com.friendmusic.c_DB.CommunicationList;
import coyc.com.friendmusic.d_Parse.Request.Request_SendMusicInfoList;
import coyc.com.friendmusic.d_Parse.Request.Request_StopMusic;
import coyc.com.friendmusic.d_Parse.interfac.OnGetNetMusicInfo;
import coyc.com.friendmusic.info.MusicInfo;
import coyc.com.friendmusic.info.NetMusicInfo;
import coyc.com.friendmusic.utils.l;

/**
 * Created by leipe on 2016/7/15.
 *
 * 解析所有文本信息（json格式）的包头解析
 */
public class TextMsgParser {

    private TextMsgParser()
    {

    }

    private static TextMsgParser instance = null;

    public static TextMsgParser getInstance() {
        if(instance == null)
        {
            synchronized (TextMsgParser.class)
            {
                if(instance == null)
                {
                    instance = new TextMsgParser();
                }
            }
        }
        return instance;
    }


    public static final String SEND_MUSIC_INFO_LIST = "send_music_info_list";
    public static final String GET_MUSIC_INFO_LIST = "get_music_info_list";

    public static final String GET_MUSIC_BY_PATH = "get_music_by_path";

    public static final String PLAY_MUSIC_BY_PATH = "PLAY_MUSIC_BY_PATH";
    public static final String PLAY_MUSIC_BY_NAME = "PLAY_MUSIC_BY_NAME";
    public static final String STOP_MUSIC = "stop_music";
    public static final String RESUME_MUSIC = "resume_music";
    public static final String UP_VOICE = "up_voice";
    public static final String DOWN_VOICE = "down_voice";
    public static final String SET_POSITION = "set_position";

    private OnGetNetMusicInfo onGetNetMusicInfo;

    public void setOnGetNetMusicInfoListener(OnGetNetMusicInfo s)
    {
        onGetNetMusicInfo = s;
    }

    public synchronized void parse(String msg, MyWifiP2pDevice myWifiP2pDevice){
        String tag = "";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(msg);
            tag = jsonObject.getString("tag");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(tag.equalsIgnoreCase(""))
        {
            l.l("TextMsgParser parse tag is null");
        }else if(tag.equalsIgnoreCase(SEND_MUSIC_INFO_LIST))
        {
            MusicInfoJsonParser musicInfoJsonParser = new MusicInfoJsonParser();

            NetMusicInfo netMusicInfo = musicInfoJsonParser.changeJsonToInfo(msg);
            netMusicInfo.device = myWifiP2pDevice;
            onGetNetMusicInfo.onGetNetMusicInfo(netMusicInfo);

        }else if(tag.equalsIgnoreCase(GET_MUSIC_INFO_LIST))
        {
            //收到获取歌曲列表的请求 这里应该将本机的歌曲列表发送给请求方  这里要记录请求方
            LocalMusicInfoManager.getInstance().initMusicInfo();

            NetMusicInfo netMusicInfo = new NetMusicInfo();

            String mac = "";

            try {
                mac = jsonObject.getString("src_mac");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            netMusicInfo.device = CommunicationList.getInstance().getFriendsByMac(mac);
            netMusicInfo.musicInfos = LocalMusicInfoManager.getInstance().musicInfos;

            if(netMusicInfo.device!=null)
            {
                new Request_SendMusicInfoList(netMusicInfo).send();
                l.l("(netMusicInfo.device!=null)");
            }else
            {
                l.l("(netMusicInfo.device==null)");
            }

        }else if(tag.equalsIgnoreCase(PLAY_MUSIC_BY_PATH)) {

            if (Player.getInstance().play_mode == Player.Play_Mode_OtherPlay)
            {
                MainActivity.mHandler.sendEmptyMessage(MainActivity.StopOtherPlay);
            }


            try {
                String path = jsonObject.getString("path");
                String title =jsonObject.getString("music_title");
                String author =jsonObject.getString("music_author");

                MusicInfo musicInfo = new MusicInfo();
                musicInfo.author = author;
                musicInfo.title = title;
                MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_NOTHING;
                MainActivity.current_playmusic = musicInfo;

                Player.getInstance().play(path);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(tag.equalsIgnoreCase(PLAY_MUSIC_BY_NAME))
        {
            try {
                String name = jsonObject.getString("music_name");
                String title =jsonObject.getString("music_title");
                String author =jsonObject.getString("music_author");
                Player.getInstance().play(Environment.getExternalStorageDirectory()+"/FriendMusic/musicCache/"+name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(tag.equalsIgnoreCase(STOP_MUSIC))
        {
            Player.getInstance().Stop();
        }else if(tag.equalsIgnoreCase(RESUME_MUSIC))
        {
            Player.getInstance().Resume();
        }else if(tag.equalsIgnoreCase(UP_VOICE))
        {

        }else if(tag.equalsIgnoreCase(DOWN_VOICE))
        {

        }else if(tag.equalsIgnoreCase(SET_POSITION))
        {

        }else if(tag.equalsIgnoreCase(GET_MUSIC_BY_PATH))
        {
            //对方请求 那就发送给对方
            String path = "";
            try {
                path = jsonObject.getString("path");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            WifiDirectManager.getInstance().sendFileByDevice(myWifiP2pDevice,path,"file_music");
        }else
        {

        }
    }
}
