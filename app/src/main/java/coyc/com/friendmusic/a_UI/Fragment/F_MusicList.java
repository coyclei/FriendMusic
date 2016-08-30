package coyc.com.friendmusic.a_UI.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import coyc.com.friendmusic.R;
import coyc.com.friendmusic.a_UI.Activity.MainActivity;
import coyc.com.friendmusic.a_UI.Adapter.MusicInfoAdapter;
import coyc.com.friendmusic.b_Music.LocalMusicInfoManager;
import coyc.com.friendmusic.b_Music.Player;
import coyc.com.friendmusic.info.MusicInfo;
import coyc.com.friendmusic.info.NetMusicInfo;
import coyc.com.friendmusic.utils.SharePreferenceUtil;
import coyc.com.friendmusic.utils.l;

/**
 * Created by leipe on 2016/3/13.
 */
public class F_MusicList extends Fragment {


    public NetMusicInfo getNetMusicInfo() {
        return netMusicInfo;
    }

    public void setNetMusicInfo(NetMusicInfo netMusicInfo) {
        this.netMusicInfo = netMusicInfo;
    }
    private NetMusicInfo netMusicInfo;
    private View mView;
    private ListView mList;
    private ImageView mIv_bc;
    private MusicInfoAdapter mAdapter;
    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置

//    private MusicInfo old_music = new MusicInfo() ;
    private SharePreferenceUtil sharePreferenceUtil;
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
        sharePreferenceUtil = new SharePreferenceUtil(getActivity(),"FriendMusic");
    }

    private void initView() {


        mIv_bc = (ImageView) mView.findViewById(R.id.im_bc);


        mList = (ListView) mView.findViewById(R.id.lv_list);
        mList.setBackgroundColor(Color.argb(40,0,0,0));
        mAdapter = new MusicInfoAdapter(netMusicInfo.musicInfos,getActivity());
        mList.setAdapter(mAdapter);

        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                PlayMusicByID(position);
            }
        });

        switch (sharePreferenceUtil.getImg())
        {
            case 1:

                setBackground(R.mipmap.b1);
                break;
            case 2:
                setBackground(R.mipmap.b2);
                break;
            case 3:
                setBackground(R.mipmap.b3);
                break;
            case 4:
                setBackground(R.mipmap.b4);
                break;
            case 5:
                setBackground(R.mipmap.b4);
                break;
            case 6:
                setBackground(R.mipmap.b6);
                break;
        }

    }

    public void PlayMusicByID(int position) {
        l.l("11 F_MusicList position"+position);
        if(position>=netMusicInfo.musicInfos.size()){
            position = 0;
        }

        switch (position)
        {
            case 1:
                setBackground(R.mipmap.b1);
                break;
            case 2:
                setBackground(R.mipmap.b2);
                break;
            case 3:
                setBackground(R.mipmap.b3);
                break;
            case 4:
                setBackground(R.mipmap.b4);
                break;
            case 5:
                setBackground(R.mipmap.b5);
                break;
            case 6:
                setBackground(R.mipmap.b6);
                break;
        }

        if(Player.getInstance().play_mode == Player.Play_Mode_OtherPlay)
        {
            StopOtherPlay();
        }

        if(netMusicInfo.musicInfos.get(position).play_state == MusicInfo.PLAY_STATE_NOTHING)
        {
            //切歌必做
            MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_NOTHING;
            MainActivity.current_playmusic = netMusicInfo.musicInfos.get(position);
            MainActivity.current_play_device = netMusicInfo.device;
            Player.getInstance().play(netMusicInfo.musicInfos.get(position).file_path);
        }else if(netMusicInfo.musicInfos.get(position).play_state == MusicInfo.PLAY_STATE_PLAYING)
        {
            Player.getInstance().Stop();
        }else if(netMusicInfo.musicInfos.get(position).play_state == MusicInfo.PLAY_STATE_STOP)
        {
            Player.getInstance().Resume();
        }
    }

    //切换外放模式为内放模式
    private void StopOtherPlay() {
        MainActivity.mHandler.sendEmptyMessage(MainActivity.StopOtherPlay);
    }

    public void setBackground(int id)
    {
        mIv_bc.setImageResource(id);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void updateList() {
        mAdapter.notifyDataSetChanged();
    }
}
