package coyc.com.friendmusic.d_Parse.Request;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;
import com.coyc.test_wifidirectmodule.wifidriect_kernel.WifiDirectManager;

import org.json.JSONException;
import org.json.JSONObject;

import coyc.com.friendmusic.d_Parse.interfac.OnCMDSendEnd;

/**
 * Created by leipe on 2016/7/15.
 *
 * 项目中所有请求推送类的父类
 *
 * 本质是满足上层需要的各种操作与需求
 *
 * 请求实际是发送一段json文本表达本机设备的需求或直接提供某些类容
 *
 */
public class Request {

//    public String mac; //发送对象

//    public String json;//发送类容

    public JSONObject jsonObject;

    public MyWifiP2pDevice device;


    public Request()
    {
        jsonObject = new JSONObject();
    }



    private OnCMDSendEnd onCMDSendEnd;

    public void setOnCMDSendEndListener(OnCMDSendEnd s)
    {
        onCMDSendEnd = s;
    }

    public void send() //发送方法
    {
        try {
            jsonObject.put("mac",device.mac);
            jsonObject.put("src_mac",WifiDirectManager.getInstance().getWFDMacAddress());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        WifiDirectManager.getInstance().sendTextByDevice(device,jsonObject.toString());// TODO: 2016/7/15 null  与mac有关
        // TODO: 2016/7/15 传递对象待完善
    }

}
