package coyc.com.friendmusic.info;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;

import java.util.ArrayList;

/**
 * Created by leipe on 2016/7/15.
 * 每个网络端设备的歌曲列表统称为NetMusicInfo
 * 一个设备可能拥有多个NetMusicInfo
 */
public class NetMusicInfo {

//    public String mac = "";//该网络设备的mac地址

    public MyWifiP2pDevice device = new MyWifiP2pDevice();//该网络设备的对象  歌单来自的设备对象
    public ArrayList<MusicInfo> musicInfos = new ArrayList<MusicInfo>();
}
