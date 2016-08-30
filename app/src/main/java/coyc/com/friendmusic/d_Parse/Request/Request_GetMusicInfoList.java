package coyc.com.friendmusic.d_Parse.Request;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;

import org.json.JSONException;
import org.json.JSONObject;

import coyc.com.friendmusic.d_Parse.TextMsgParser;

/**
 * Created by leipe on 2016/7/15.
 */
public class Request_GetMusicInfoList extends Request {

    public Request_GetMusicInfoList(MyWifiP2pDevice myWifiP2pDevice)
    {
        super();
        init(myWifiP2pDevice);
    }

    private void init(MyWifiP2pDevice myWifiP2pDevice) {
        device = myWifiP2pDevice;
        try {
            jsonObject.put("tag", TextMsgParser.GET_MUSIC_INFO_LIST);

            // TODO: 2016/7/15 这里还可以发送希望获取文件的规则 如只获取10条，文件大小为10M以下的 周杰伦的歌曲等

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
