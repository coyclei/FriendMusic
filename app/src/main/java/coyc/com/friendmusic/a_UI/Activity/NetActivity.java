package coyc.com.friendmusic.a_UI.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.OnGroupListChangeListener;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.OnPeersDataChangeListener;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.OnReceiveDataListener;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.WifiDirectManager;

import coyc.com.friendmusic.R;
import coyc.com.friendmusic.a_UI.Adapter.DeviceList_GroupAdapter;
import coyc.com.friendmusic.a_UI.Adapter.DeviceList_PeersAdapter;
import coyc.com.friendmusic.d_Parse.TextMsgParser;

public class NetActivity extends Activity {


    private WifiDirectManager wifiDirectManager;
    private DeviceList_PeersAdapter peersListAdapter;
    private DeviceList_GroupAdapter groupListAdapter;

    private ListView LV_peers;
    private ListView LV_group;
    private String TAG = "coyc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);

        wifiDirectManager = WifiDirectManager.getInstance();
        wifiDirectManager.init(this, new OnPeersDataChangeListener() {
            @Override
            public void onPeersDataChange() {
                peersListAdapter.notifyDataSetChanged();
            }
        }, new OnGroupListChangeListener() {
            @Override

            public void onGroupListChange() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),"group list change", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "run: runOnUiThread group list change");
                        groupListAdapter= new DeviceList_GroupAdapter(wifiDirectManager.getGroup(),getBaseContext());
                        LV_group.setAdapter(groupListAdapter);
                    }
                });
            }
        }, new OnReceiveDataListener() {
            @Override
            public void onReceiveText(final String text, final MyWifiP2pDevice myWifiP2pDevice) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: get text ");
                        Toast.makeText(getBaseContext(),text, Toast.LENGTH_SHORT).show();

                        TextMsgParser.getInstance().parse(text,myWifiP2pDevice);
                    }
                });
            }

            @Override
            public void onReceiveByte(final byte[] bytes, MyWifiP2pDevice myWifiP2pDevice) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: get text ");
                        Toast.makeText(getBaseContext(),"onGetByte"+bytes[0], Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onReceiveFile(final String s, MyWifiP2pDevice myWifiP2pDevice,String tag) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: get file ");
                        Toast.makeText(getBaseContext(),"收到文件"+s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        peersListAdapter = new DeviceList_PeersAdapter(wifiDirectManager.getPeers(),this);
        groupListAdapter= new DeviceList_GroupAdapter(wifiDirectManager.getGroup(),this);

        LV_peers = (ListView) findViewById(R.id.lv_peers);
        LV_group = (ListView) findViewById(R.id.lv_group);

        LV_peers.setAdapter(peersListAdapter);
        LV_group.setAdapter(groupListAdapter);

        if(wifiDirectManager.isWifiCanUse())
        {
            ((TextView)findViewById(R.id.open_or_close)).setText("WiFiDirect已开启...点击关闭");
        }else
        {
            ((TextView)findViewById(R.id.open_or_close)).setText("WiFiDirect已关闭...点击开启");
        }

        LV_peers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wifiDirectManager.connect(wifiDirectManager.getPeers().get(position));
            }
        });

        LV_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wifiDirectManager.sendTextByDevice(wifiDirectManager.getGroup().get(position),"hello!");

//                byte[] test = new byte[]{0X35, 0x23};
//                wifiDirectManager.sendBufferByDevice(wifiDirectManager.getGroup().get(position),test);


            }
        });
        //长按取消连接
        LV_group.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                wifiDirectManager.disConnect();
                Toast.makeText(NetActivity.this,"正在执行断开连接操作...", Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.open_or_close:

                if(wifiDirectManager.isWifiCanUse())
                {
                    wifiDirectManager.setWifiCanUse(false);
                    ((TextView)findViewById(R.id.open_or_close)).setText("WiFiDirect已关闭...点击开启");
                }else
                {
                    wifiDirectManager.setWifiCanUse(true);
                    ((TextView)findViewById(R.id.open_or_close)).setText("WiFiDirect已开启...点击关闭");
                }
                break;
            case R.id.search_or_stop:

                if(wifiDirectManager.isWifiCanUse())
                {
                    wifiDirectManager.startSearch();
                }
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
