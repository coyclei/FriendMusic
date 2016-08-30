package coyc.com.friendmusic.d_Parse.Request;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;

import org.json.JSONException;
import org.json.JSONObject;

import coyc.com.friendmusic.d_Parse.TextMsgParser;

/**
 * Created by leipe on 2016/7/15.
 */
public class Request_PlayMusic_byPath extends Request {


    public Request_PlayMusic_byPath(MyWifiP2pDevice device_, String path,String title,String author,int id)
    {
        super();
        device = device_;
        try {
            jsonObject.put("tag", TextMsgParser.PLAY_MUSIC_BY_PATH);
            jsonObject.put("path",path);
            jsonObject.put("music_title",title);
            jsonObject.put("music_author",author);
            jsonObject.put("id",id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
