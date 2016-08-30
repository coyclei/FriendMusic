package coyc.com.friendmusic.d_Parse.Request;

import coyc.com.friendmusic.d_Parse.MusicInfoJsonParser;
import coyc.com.friendmusic.info.NetMusicInfo;

/**
 * Created by leipe on 2016/7/15.
 */
public class Request_SendMusicInfoList extends Request {


    public Request_SendMusicInfoList(NetMusicInfo netMusicInfo)
    {
        super();
        device = netMusicInfo.device;
        jsonObject = MusicInfoJsonParser.changeInfoToJsonObject(netMusicInfo);
    }
}
