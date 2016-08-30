package coyc.com.friendmusic.c_DB;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.WifiDirectManager;

import java.util.ArrayList;

/**
 * Created by leipe on 2016/7/19.
 *
 * 用于管理通信对象列表
 *
 * 首先其管理了所有网络中的好友列表
 *
 * 并且他提供选中部分好友的功能（可能某些音乐不需要发送给所有人）
 *
 * 并且最关键这列表中要剔除本身
 *
 */
public class CommunicationList {

    private static CommunicationList instance;

    public static CommunicationList getInstance() {
        if (instance == null) {
            synchronized (CommunicationList.class) {
                if (instance == null) {
                    instance = new CommunicationList();
                }
            }
        }
        return instance;
    }

    private CommunicationList() {

        initFriends();

    }

    /**
     * 初始化好友列表，并除去本身
     */
    private void initFriends() {
        String mac_self = WifiDirectManager.getInstance().getWFDMacAddress();
        friends.clear();

        int size = WifiDirectManager.getInstance().getGroup().size();
        for (int i = 0 ;i<size;i++)
        {
            if(mac_self.equalsIgnoreCase(WifiDirectManager.getInstance().getGroup().get(i).mac))
            {
            }else
            {
                friends.add(WifiDirectManager.getInstance().getGroup().get(i));
            }
        }
    }

    /**
     * 通信好友列表
     *
     * 默认所有操作对所有成员有效（如推送歌单，播放音乐，获取歌单也一样）
     */
    private ArrayList<MyWifiP2pDevice> friends = new ArrayList<MyWifiP2pDevice>();

    public ArrayList<MyWifiP2pDevice> getFriends()
    {
        initFriends();
        return friends;
    }

    public MyWifiP2pDevice getFriendsByMac(String mac)
    {
        initFriends();
        int size = friends.size();
        for(int i = 0;i<size;i++)
        {
            if(mac.equalsIgnoreCase(friends.get(i).mac))
            {
                return friends.get(i);
            }
        }
        return null;
    };








}
