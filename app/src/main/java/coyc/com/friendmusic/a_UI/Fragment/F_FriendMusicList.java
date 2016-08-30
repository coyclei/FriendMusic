package coyc.com.friendmusic.a_UI.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import coyc.com.friendmusic.R;
import coyc.com.friendmusic.a_UI.Activity.MainActivity;
import coyc.com.friendmusic.a_UI.Adapter.MusicInfoAdapter;
import coyc.com.friendmusic.b_Music.LocalMusicInfoManager;
import coyc.com.friendmusic.b_Music.Player;
import coyc.com.friendmusic.d_Parse.Request.Request_GetMusicByPath;
import coyc.com.friendmusic.d_Parse.Request.Request_PlayMusic_byPath;
import coyc.com.friendmusic.d_Parse.Request.Request_ResumeMusic;
import coyc.com.friendmusic.d_Parse.Request.Request_StopMusic;
import coyc.com.friendmusic.info.MusicInfo;
import coyc.com.friendmusic.info.NetMusicInfo;

/**
 * Created by leipe on 2016/3/13.
 */
public class F_FriendMusicList extends Fragment {


    public NetMusicInfo getNetMusicInfo() {
        return netMusicInfo;
    }

    public void setNetMusicInfo(NetMusicInfo netMusicInfo) {
        this.netMusicInfo = netMusicInfo;
    }

    private NetMusicInfo netMusicInfo;
    private View mView;

    private ListView mList;

    private MusicInfoAdapter mAdapter;

    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.f_all_layout, container, false);


        initData();

        initView();

        return mView;
    }


    public static final int Update_List = 0;

    private void initData() {

        LocalMusicInfoManager.getInstance().initMusicInfo();
        String musicCachePath = Environment.getExternalStorageDirectory()+"/FriendMusic/musicCache";
        File[] musicL =  GetAllFileInFile(musicCachePath);
        if(musicL!=null)
        {
            int f_size = musicL.length;
            for(int i = 0;i<f_size;i++)
            {
                int m_size = netMusicInfo.musicInfos.size();
                for(int j = 0;j<m_size;j++)
                {
                    if(musicL[i].getName().equalsIgnoreCase(netMusicInfo.musicInfos.get(j).file_name))
                    {
                        netMusicInfo.musicInfos.get(j).isSaveInLocal = true;
                        break;
                    }
                }
            }
        }
    }

    public static synchronized File[] GetAllFileInFile(String path) {

        File catalogFile = new File(path);// 目录锟侥硷拷锟斤拷
        if(!catalogFile.exists())
        {
                catalogFile.mkdirs();
        }
        File[] files = catalogFile.listFiles();
        return files;
    }

    private void initView() {

        mList = (ListView) mView.findViewById(R.id.lv_list);
        mAdapter = new MusicInfoAdapter(netMusicInfo.musicInfos, getActivity());
        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MainActivity.mHandler.sendEmptyMessage(MainActivity.ShowModeText);
                PlayMusicByID(position);

            }
        });

        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainActivity.mHandler.sendEmptyMessage(MainActivity.ShowModeText);
                if(netMusicInfo.musicInfos.get(i).isSaveInLocal)//同时播放
                {

                    if (netMusicInfo.musicInfos.get(i).play_state == MusicInfo.PLAY_STATE_NOTHING) {
                        MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_NOTHING;
                        MainActivity.current_playmusic = netMusicInfo.musicInfos.get(i);
                        MainActivity.current_play_device = netMusicInfo.device;
                        Player.getInstance().Stop();

                        new Request_PlayMusic_byPath(netMusicInfo.device, netMusicInfo.musicInfos.get(i).file_path, netMusicInfo.musicInfos.get(i).title, netMusicInfo.musicInfos.get(i).author,netMusicInfo.musicInfos.get(i).id).send();
                        Player.getInstance().play(Environment.getExternalStorageDirectory() + "/FriendMusic/musicCache/" + netMusicInfo.musicInfos.get(i).file_name);
                        MainActivity.mHandler.sendEmptyMessage(MainActivity.PlayNetMusic);
                    }else if (netMusicInfo.musicInfos.get(i).play_state == MusicInfo.PLAY_STATE_PLAYING) {

                        new Request_StopMusic(netMusicInfo.device).send();
                        MainActivity.mHandler.sendEmptyMessage(MainActivity.StopPlayNetMusic);
                        Player.getInstance().Stop();
                        
                    }else if (netMusicInfo.musicInfos.get(i).play_state == MusicInfo.PLAY_STATE_STOP) {
                        
                        new Request_ResumeMusic(netMusicInfo.device).send();
                        Player.getInstance().Resume();
                        MainActivity.mHandler.sendEmptyMessage(MainActivity.ResumePlayNetMusic);
                    }
                    
                }
                return true;
            }
        });
    }

    public void PlayMusicByID(int position) {
        if(position>=netMusicInfo.musicInfos.size()){
            position = 0;
        }

        if(Player.getInstance().play_mode == Player.Play_Mode_OtherPlay)
        {
            if (netMusicInfo.musicInfos.get(position).play_state == MusicInfo.PLAY_STATE_NOTHING) {
                //切歌必做
                MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_NOTHING;
                MainActivity.current_playmusic = netMusicInfo.musicInfos.get(position);
                MainActivity.current_play_device = netMusicInfo.device;
                Player.getInstance().Stop();
                new Request_PlayMusic_byPath(netMusicInfo.device, netMusicInfo.musicInfos.get(position).file_path, netMusicInfo.musicInfos.get(position).title, netMusicInfo.musicInfos.get(position).author,netMusicInfo.musicInfos.get(position).id).send();
                MainActivity.mHandler.sendEmptyMessage(MainActivity.PlayNetMusic);
            } else if (netMusicInfo.musicInfos.get(position).play_state == MusicInfo.PLAY_STATE_PLAYING) {
                new Request_StopMusic(netMusicInfo.device).send();
                MainActivity.mHandler.sendEmptyMessage(MainActivity.StopPlayNetMusic);
            } else if (netMusicInfo.musicInfos.get(position).play_state == MusicInfo.PLAY_STATE_STOP) {
                Player.getInstance().Stop();
                new Request_ResumeMusic(netMusicInfo.device).send();
                MainActivity.mHandler.sendEmptyMessage(MainActivity.ResumePlayNetMusic);
            }
        }else
        {
            if (netMusicInfo.musicInfos.get(position).isSaveInLocal) {

                if (netMusicInfo.musicInfos.get(position).play_state == MusicInfo.PLAY_STATE_NOTHING) {
                    //切歌必做
                    MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_NOTHING;
                    MainActivity.current_playmusic = netMusicInfo.musicInfos.get(position);
                    MainActivity.current_play_device = netMusicInfo.device;
                    Player.getInstance().play(Environment.getExternalStorageDirectory() + "/FriendMusic/musicCache/" + netMusicInfo.musicInfos.get(position).file_name);
                } else if (netMusicInfo.musicInfos.get(position).play_state == MusicInfo.PLAY_STATE_PLAYING) {
                    Player.getInstance().Stop();
                } else if (netMusicInfo.musicInfos.get(position).play_state == MusicInfo.PLAY_STATE_STOP) {
                    Player.getInstance().Resume();
                }
            } else {
                MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_NOTHING;
                MainActivity.current_playmusic = netMusicInfo.musicInfos.get(position);
                MainActivity.current_play_device = netMusicInfo.device;

                MainActivity.mHandler.sendEmptyMessage(MainActivity.WaitDownLoad);
                Toast.makeText(getActivity(), "正在缓冲...请稍后", Toast.LENGTH_SHORT).show();
                new Request_GetMusicByPath(netMusicInfo.device, netMusicInfo.musicInfos.get(position).file_path).send();
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void updateList() {
        mAdapter.notifyDataSetChanged();
    }
}
