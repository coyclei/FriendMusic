package coyc.com.friendmusic.a_UI.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.WifiDirectManager;

import java.util.ArrayList;

import coyc.com.friendmusic.R;
import coyc.com.friendmusic.a_UI.Adapter.MyPagerAdapter;
import coyc.com.friendmusic.a_UI.Fragment.F_FriendMusicList;
import coyc.com.friendmusic.a_UI.Fragment.F_MusicList;
import coyc.com.friendmusic.a_UI.Fragment.F_Set;
import coyc.com.friendmusic.b_Music.LocalMusicInfoManager;
import coyc.com.friendmusic.b_Music.NetMusicInfoManager;
import coyc.com.friendmusic.b_Music.Player;
import coyc.com.friendmusic.d_Parse.Request.Request_StopMusic;
import coyc.com.friendmusic.d_Parse.TextMsgParser;
import coyc.com.friendmusic.d_Parse.interfac.OnGetNetMusicInfo;
import coyc.com.friendmusic.info.MusicInfo;
import coyc.com.friendmusic.info.NetMusicInfo;
import coyc.com.friendmusic.utils.l;

/**
 * 主界面
 * 包括4个Fragment与底部操作栏
 * 4个Fragment分别是“全部活动”，“已加入”，“创建”，“个人信息”
 */
public class MainActivity extends FragmentActivity {


    public static final int CHANGE_PAGE = 1;
    public static final int PlayNetMusic = 2;
    public static final int StopPlayNetMusic = 3;
    public static final int UpDate_NetActive_List = 4;
    public static final int ResumePlay = 5;
    public static final int UpDate_CreateActive_List = 6;
    public static final int SetTitle = 7;
    public static final int SetMusicTitle = 8;
    public static final int StopPlay = 9;
    public static final int ResumePlayNetMusic = 10;
    public static final int WaitDownLoad = 11;
    public static final int WaitDownLoadIsOK = 12;
    public static final int StopOtherPlay = 13;
    public static final int StartOtherPlay = 14;
    public static final int MusicPlayOver = 15;
    public static final int ShowModeText = 16;

    private ViewPager mViewPager;
    private RelativeLayout mRl_pager;
    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private PagerAdapter mPagerAdapter;
    public static Handler mHandler;

    private F_Set mF_set;
    private F_MusicList mF_music;

    private RelativeLayout mRl_topBar;
    private TextView mTv_title;
    private TextView mTv_PlayMode;
    private RelativeLayout mRl_search;

    private TextView mTv_music_title;

    public static MusicInfo current_playmusic = new MusicInfo();
    public static MyWifiP2pDevice current_play_device = new MyWifiP2pDevice();//系统当前播放的音乐属于哪个设备的

    public static boolean isLocalPlay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        LocalMusicInfoManager.getInstance().init(getBaseContext());
        WifiDirectManager.getInstance().setFileSaveDir(Environment.getExternalStorageDirectory()+"/FriendMusic/musicCache");
        Player.getInstance().initPlayer(this);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CHANGE_PAGE:
                        mViewPager.setCurrentItem(msg.arg1, true);
                        break;
                    case PlayNetMusic:
                        mTv_music_title.setText(current_playmusic.title+" "+current_playmusic.author);
                        mTv_music_title.setTextColor(Color.argb(255,255,255,255));
                        mTv_music_title.setVisibility(View.VISIBLE);
                        MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_PLAYING;
                        isLocalPlay = false;
                        updateFragmentList();
                        break;
                    case StopPlayNetMusic:
                        mTv_music_title.setTextColor(Color.argb(125,255,255,255));
                        mTv_music_title.setVisibility(View.VISIBLE);
                        MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_STOP;
                        updateFragmentList();
                        isLocalPlay = false;
                        break;
                    case ResumePlayNetMusic:
                        mTv_music_title.setTextColor(Color.argb(255,255,255,255));
                        mTv_music_title.setVisibility(View.VISIBLE);
                        MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_PLAYING;
                        updateFragmentList();
                        isLocalPlay = false;
                        break;
                    case UpDate_CreateActive_List:
                        break;
                    case ResumePlay:
                        mTv_music_title.setTextColor(Color.argb(255,255,255,255));
                        mTv_music_title.setVisibility(View.VISIBLE);
                        MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_PLAYING;
                        updateFragmentList();
                        break;
                    case WaitDownLoad:
                        mTv_music_title.setTextColor(Color.argb(255,0,162,232));
                        mTv_music_title.setText(current_playmusic.title+" "+current_playmusic.author);
                        MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_WAIT_DOWNLOAD;
                        updateFragmentList();

                        break;
                    case WaitDownLoadIsOK:
                        updateFragmentList();
                        Player.getInstance().play(Environment.getExternalStorageDirectory() + "/FriendMusic/musicCache/" + current_playmusic.file_name);
                        break;
                    case SetMusicTitle:
                        MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_PLAYING;
                        mTv_music_title.setTextColor(Color.argb(255,255,255,255));
                        mTv_music_title.setVisibility(View.VISIBLE);
                        mTv_music_title.setText(current_playmusic.title+" "+current_playmusic.author);
                        updateFragmentList();
                        break;
                    case StopPlay:

                        mTv_music_title.setTextColor(Color.argb(255,192,192,192));
                        mTv_music_title.setVisibility(View.VISIBLE);
                        MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_STOP;
                        updateFragmentList();
                        break;
                    case StopOtherPlay:

                        StopOtherPlay();
                        break;

                    case StartOtherPlay:

                        StartOtherPlay();
                        break;

                    case MusicPlayOver:
                        l.l("11 Hander MusicOver ");
                        int size = mFragments.size();
                        l.l("11 current_play_device.mac"+current_play_device.mac);
                        l.l("11 current_play_device.name"+current_play_device.name);
                        l.l("11 mF_music.getNetMusicInfo().device.mac"+mF_music.getNetMusicInfo().device.mac);

                        if(current_play_device.mac.equalsIgnoreCase(WifiDirectManager.getInstance().getWFDMacAddress())||current_play_device.mac.equals(""))
                        {
                            l.l("11 本机的音乐 下一首");
                            int a = current_playmusic.id+1;
                            mF_music.PlayMusicByID(a);
                        }
                        for(int i = 2;i<size;i++)
                        {
                            l.l("11 (F_FriendMusicList)mFragments.get(i)).getNetMusicInfo().device.mac)"+((F_FriendMusicList)mFragments.get(i)).getNetMusicInfo().device.mac+  "    i"+i);
                            if(current_play_device.mac.equalsIgnoreCase(((F_FriendMusicList)mFragments.get(i)).getNetMusicInfo().device.mac))
                            {
                                int a = current_playmusic.id+1;
                                l.l("11 不是本机的音乐 下一首"+current_play_device.name);
                                ((F_FriendMusicList)mFragments.get(i)).PlayMusicByID(a);
                                break;
                            }
                        }
                        break;
                    case ShowModeText:
                        mTv_PlayMode.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };


        mF_set = new F_Set();
        mF_music = new F_MusicList();

        NetMusicInfo netMusicInfo = new NetMusicInfo();
        netMusicInfo.musicInfos = LocalMusicInfoManager.getInstance().musicInfos;
        mF_music.setNetMusicInfo(netMusicInfo);

        mFragments.add(mF_set);
        mFragments.add(mF_music);

        TextMsgParser.getInstance().setOnGetNetMusicInfoListener(new OnGetNetMusicInfo() {
            @Override
            public synchronized void onGetNetMusicInfo(NetMusicInfo netMusicInfo) {
                int size = mFragments.size();
                for(int i = 2;i<size;i++)
                {
                    if(((F_FriendMusicList)(mFragments.get(i))).getNetMusicInfo().device.mac.equalsIgnoreCase(netMusicInfo.device.mac))
                    {
                        return;
                    }
                }
                //NetMusicInfoManager开始大显神威！！！
                NetMusicInfoManager.getInstance().netMusicInfoMap.put(netMusicInfo.device.mac,netMusicInfo);

                F_FriendMusicList addF = new F_FriendMusicList();
                addF.setNetMusicInfo(NetMusicInfoManager.getInstance().netMusicInfoMap.get(netMusicInfo.device.mac));
                mFragments.add(addF);
                mTv_PlayMode.setVisibility(View.VISIBLE);
                mPagerAdapter.notifyDataSetChanged();
                mViewPager.setCurrentItem(mFragments.size()-1,true);
            }
        });
    }


    private void initView() {
        mRl_topBar = (RelativeLayout) findViewById(R.id.rl_titlebar);
        mTv_PlayMode = (TextView) findViewById(R.id.tv_play_mode);

        mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mViewPager.getCurrentItem()>=2)
                {
                    if(Player.getInstance().play_mode == Player.Play_Mode_OtherPlay)
                    {
                        StopOtherPlay();
                    }else
                    {
                        StartOtherPlay();
                    }
                }
            }
        });
        mRl_search = (RelativeLayout) findViewById(R.id.rl_search);
        mRl_pager = (RelativeLayout) findViewById(R.id.rl_pager);

        mTv_music_title = (TextView) findViewById(R.id.tv_music_title1);
        mTv_music_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Player.getInstance().isIsplaying())
                {

                    Player.getInstance().Stop();
                }else
                {
                    Player.getInstance().Resume();
                }
            }
        });

        mViewPager = (ViewPager) findViewById(R.id.vp_pager);

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(3);//设置最大页面缓存数(不含当前页面)
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                updateButtonBarByCurrentPageItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTv_title.setText("我的音乐");


    }

    private void StartOtherPlay() {
        Player.getInstance().play_mode = Player.Play_Mode_OtherPlay;
        mTv_PlayMode.setText("外放模式");
        if(mViewPager.getCurrentItem()>=2)
        {
            mTv_PlayMode.setVisibility(View.VISIBLE);
        }else
        {
            mTv_PlayMode.setVisibility(View.INVISIBLE);
        }
        Player.getInstance().Stop();
    }
    private void StopOtherPlay() {
        Player.getInstance().play_mode = Player.Play_Mode_SelfPlay;
        mTv_PlayMode.setText("自放模式");
        if(mViewPager.getCurrentItem()>=2)
        {
            mTv_PlayMode.setVisibility(View.VISIBLE);
        }else
        {
            mTv_PlayMode.setVisibility(View.INVISIBLE);
        }
        new Request_StopMusic(current_play_device).send();
        current_play_device = WifiDirectManager.getInstance().myWifiP2pDevice;
    }


    /**
     * 页面滑动时，改变底部选中区域颜色
     */
    private void updateButtonBarByCurrentPageItem(int currentpage) {
        setBack();
        switch (currentpage) {
            case 0:
                mTv_title.setText("我的好友");
                mRl_search.setVisibility(View.GONE);
                mTv_PlayMode.setVisibility(View.INVISIBLE);
                break;
            case 1:
                mTv_title.setText("我的音乐");
                mRl_search.setVisibility(View.GONE);
                mTv_PlayMode.setVisibility(View.INVISIBLE);
                break;
            default:
                mTv_title.setText(((F_FriendMusicList)mFragments.get(currentpage)).getNetMusicInfo().device.name+"的音乐");
                mTv_PlayMode.setVisibility(View.VISIBLE);
                mRl_search.setVisibility(View.GONE);

                break;
        }
    }

    /**
     * 将按钮颜色设置为初始状态
     */
    private void setBack() {

    }

    public void onClick(View v) {

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void updateFragmentList()
    {
        mF_music.updateList();
        int size  =  mFragments.size();
        for(int i = 2;i<size;i++)
        {
            ((F_FriendMusicList)mFragments.get(i)).updateList();
        }
    }
}
