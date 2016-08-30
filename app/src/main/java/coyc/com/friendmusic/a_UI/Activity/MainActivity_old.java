package coyc.com.friendmusic.a_UI.Activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.WifiDirectManager;

import java.io.File;

import coyc.com.friendmusic.R;
import coyc.com.friendmusic.a_UI.Adapter.MusicInfoAdapter;
import coyc.com.friendmusic.c_DB.CommunicationList;
import coyc.com.friendmusic.d_Parse.Request.Request_GetMusicInfoList;
import coyc.com.friendmusic.d_Parse.Request.Request_PlayMusic_byName;
import coyc.com.friendmusic.d_Parse.Request.Request_PlayMusic_byPath;
import coyc.com.friendmusic.b_Music.LocalMusicInfoManager;
import coyc.com.friendmusic.b_Music.Player;
import coyc.com.friendmusic.d_Parse.interfac.OnGetNetMusicInfo;
import coyc.com.friendmusic.d_Parse.TextMsgParser;
import coyc.com.friendmusic.info.NetMusicInfo;

public class MainActivity_old extends ActionBarActivity {

    private ListView list;

//    public static Player play;
    public static boolean isPlaytoher = false;
    private MusicInfoAdapter adapter;


    private Button getMusic;
    private boolean showSelfMusic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        initData();
        init();

//        play = new Player(getBaseContext());
    }

    private void initData() {
        LocalMusicInfoManager.getInstance().init(getBaseContext());
        WifiDirectManager.getInstance().setFileSaveDir(Environment.getExternalStorageDirectory()+"/FriendMusic/musicCache");
    }

    private void init() {

        list = (ListView) findViewById(R.id.list);
        adapter = new MusicInfoAdapter(LocalMusicInfoManager.getInstance().musicInfos,getBaseContext());
        list.setAdapter(adapter);

        TextMsgParser.getInstance().setOnGetNetMusicInfoListener(new OnGetNetMusicInfo() {
            @Override
            public void onGetNetMusicInfo(NetMusicInfo netMusicInfo) {

                adapter = new MusicInfoAdapter(netMusicInfo.musicInfos,getBaseContext());
                list.setAdapter(adapter);

            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //发送播放请求
                // TODO: 2016/7/15 目前播放对象由MainActivity中的send——p决定  待修改
                // TODO: 2016/7/19 目前只是发送给第一个好友
                if(showSelfMusic)
                {
                    WifiDirectManager.getInstance().sendFileByDevice(CommunicationList.getInstance().getFriends().get(0),LocalMusicInfoManager.getInstance().musicInfos.get(i).file_path,"");
                }else
                {
                    new Request_PlayMusic_byPath(CommunicationList.getInstance().getFriends().get(0),adapter.info.get(i).file_path,adapter.info.get(i).title,adapter.info.get(i).author,adapter.info.get(i).id).send();
                }

            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(showSelfMusic)
                {
                    File f = new File(adapter.info.get(i).file_path);

                    new Request_PlayMusic_byName(CommunicationList.getInstance().getFriends().get(0),f.getName()).send();

//                    MainActivity_old.play.play(adapter.info.get(i).file_path);

                }else
                {

                }

                return true;
            }
        });

        getMusic = (Button) findViewById(R.id.open);
        getMusic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                showSelfMusic = true;
                Toast.makeText(getBaseContext(),"longclick",Toast.LENGTH_SHORT).show();
                LocalMusicInfoManager.getInstance().initMusicInfo();
                adapter = new MusicInfoAdapter(LocalMusicInfoManager.getInstance().musicInfos,getBaseContext());
                list.setAdapter(adapter);
                return true;
            }
        });

    }


    public void OnClick(View view)
    {
        switch (view.getId())
        {
            case R.id.open:

                showSelfMusic = false;

//                LocalMusicInfoManager.getInstance().initMusicInfo();
//                LocalMusicInfoManager.getInstance().logMusicInfo();
//
//                MusicInfoJsonParser musicInfoJsonParser = new MusicInfoJsonParser();
//
//                NetMusicInfo netMusicInfo = new NetMusicInfo();
//                netMusicInfo.mac = "6a5as8dasas8d";
//                netMusicInfo.musicInfos = LocalMusicInfoManager.getInstance().musicInfos;
//                String temp = musicInfoJsonParser.changeInfoToJson(netMusicInfo);
//                l.l(temp);
//
//                l.logMusicInfo(musicInfoJsonParser.changeJsonToInfo(temp).musicInfos);
//
//                WifiDirectManager.getInstance().sendTextByDevice(CommunicationList.getInstance().getFriends().get(0),temp);



                new Request_GetMusicInfoList(CommunicationList.getInstance().getFriends().get(0)).send();


                break;


            case R.id.scan:

                Intent intent = new Intent(MainActivity_old.this,NetActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
