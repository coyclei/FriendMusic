package coyc.com.friendmusic.b_Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import coyc.com.friendmusic.info.NetMusicInfo;

/**
 * Created by leipe on 2016/7/15.
 *
 * 管理网络端歌曲信息
 * 可能网络端的歌曲来自很多不同的设备
 * 这些不同的歌曲列表以列表进行区分
 *
 */
public class NetMusicInfoManager {

    private static NetMusicInfoManager instance;

    public static NetMusicInfoManager getInstance() {
        if (instance == null) {
            synchronized (NetMusicInfoManager.class) {
                if (instance == null) {
                    instance = new NetMusicInfoManager();
                }
            }
        }
        return instance;
    }

    private NetMusicInfoManager() {

    }


    /**
     * 网络端列表集合
     */
    public Map<String,NetMusicInfo> netMusicInfoMap = new HashMap<String, NetMusicInfo>();



}
