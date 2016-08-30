package coyc.com.friendmusic.d_Parse.Request;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;

import org.json.JSONException;

import coyc.com.friendmusic.d_Parse.TextMsgParser;

/**
 * Created by leipe on 2016/7/15.
 * 请求获取别人的音乐  即发起下载请求
 */
public class Request_GetMusicByPath extends Request {

    private String path = "";
    public Request_GetMusicByPath(MyWifiP2pDevice myWifiP2pDevice,String path)
    {
        super();
        this.path = path;
        init(myWifiP2pDevice);
    }

    private void init(MyWifiP2pDevice myWifiP2pDevice) {
        device = myWifiP2pDevice;
        try {
            jsonObject.put("tag", TextMsgParser.GET_MUSIC_BY_PATH);
            jsonObject.put("path",path);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
