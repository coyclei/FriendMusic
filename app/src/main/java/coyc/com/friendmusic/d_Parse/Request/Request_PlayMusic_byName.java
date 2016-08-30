package coyc.com.friendmusic.d_Parse.Request;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;

import org.json.JSONException;

import coyc.com.friendmusic.d_Parse.TextMsgParser;
import coyc.com.friendmusic.d_Parse.interfac.OnCMDSendEnd;

/**
 * Created by leipe on 2016/7/15.
 */
public class Request_PlayMusic_byName extends Request {



    public Request_PlayMusic_byName(MyWifiP2pDevice device_, String path)
    {
        super();
        device = device_;
        try {
            jsonObject.put("tag", TextMsgParser.PLAY_MUSIC_BY_NAME);
            jsonObject.put("music_name",path);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
