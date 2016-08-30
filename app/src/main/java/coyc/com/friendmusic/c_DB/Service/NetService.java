package coyc.com.friendmusic.c_DB.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.OnGroupListChangeListener;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.OnPeersDataChangeListener;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.OnReceiveDataListener;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.WifiDirectManager;

import coyc.com.friendmusic.a_UI.Adapter.DeviceList_GroupAdapter;
import coyc.com.friendmusic.d_Parse.TextMsgParser;

/**
 * 本服务用于管理网络的连接与扫描  并且负责文件的传输下载 存储等
 */
public class NetService extends Service {


    private WifiDirectManager wifiDirectManager;

    public NetService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        initData();



        return super.onStartCommand(intent, flags, startId);
    }

    private void initData() {
        wifiDirectManager = WifiDirectManager.getInstance();

        wifiDirectManager.init(getBaseContext(), new OnPeersDataChangeListener() {
            @Override
            public void onPeersDataChange() {
//                peersListAdapter.notifyDataSetChanged();
            }
        }, new OnGroupListChangeListener() {
            @Override

            public void onGroupListChange() {


//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getBaseContext(),"group list change", Toast.LENGTH_SHORT).show();
//                        groupListAdapter= new DeviceList_GroupAdapter(wifiDirectManager.getGroup(),getBaseContext());
//                        mLv_friends.setAdapter(groupListAdapter);
//                    }
//                });
            }

        }, new OnReceiveDataListener() {
            @Override
            public void onReceiveText(final String text, final MyWifiP2pDevice myWifiP2pDevice) {

                Toast.makeText(getBaseContext(),text, Toast.LENGTH_SHORT).show();
                TextMsgParser.getInstance().parse(text,myWifiP2pDevice);

            }

            @Override
            public void onReceiveByte(final byte[] bytes, MyWifiP2pDevice myWifiP2pDevice) {
                Toast.makeText(getBaseContext(),"onGetByte"+bytes[0], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceiveFile(final String s, MyWifiP2pDevice myWifiP2pDevice,String tag) {
                Toast.makeText(getBaseContext(),"收到文件"+s, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
