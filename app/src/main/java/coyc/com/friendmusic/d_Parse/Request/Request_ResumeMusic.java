package coyc.com.friendmusic.d_Parse.Request;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;

import org.json.JSONException;
import org.json.JSONObject;

import coyc.com.friendmusic.d_Parse.TextMsgParser;

/**
 * Created by leipe on 2016/7/15.
 */
public class Request_ResumeMusic extends Request {


    public Request_ResumeMusic(MyWifiP2pDevice device_)
    {
        super();
        device = device_;

        try {
            jsonObject.put("tag", TextMsgParser.RESUME_MUSIC);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
