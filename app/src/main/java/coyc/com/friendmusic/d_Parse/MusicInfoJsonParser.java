package coyc.com.friendmusic.d_Parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import coyc.com.friendmusic.info.MusicInfo;
import coyc.com.friendmusic.info.NetMusicInfo;

/**
 * Created by leipe on 2016/7/15.
 *
 * 负者解析歌曲信息
 */
public class MusicInfoJsonParser {

    /**
     * 将歌单信息转换为json文本
     * @param netMusicInfo
     * @return
     */
    public synchronized static String changeInfoToJson(NetMusicInfo netMusicInfo)
    {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("tag",TextMsgParser.SEND_MUSIC_INFO_LIST);

        JSONArray jsonArray = new JSONArray();

        int size = netMusicInfo.musicInfos.size();
        for(int i = 0;i<size;i++)
        {
            JSONObject object = new JSONObject();
            MusicInfo musicInfo = netMusicInfo.musicInfos.get(i);
            object.put("title",musicInfo.title);
            object.put("author",musicInfo.author);
            object.put("duration",musicInfo.duration);
            object.put("file_size",musicInfo.file_size);
            object.put("file_path",musicInfo.file_path);
            object.put("file_name",musicInfo.file_name);
            object.put("id",musicInfo.id);

            jsonArray.put(object);
        }
        jsonObject.put("list",jsonArray);

    } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    public synchronized static JSONObject changeInfoToJsonObject(NetMusicInfo netMusicInfo)
    {
        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject("");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        try {
            jsonObject = new JSONObject(changeInfoToJson(netMusicInfo));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 将歌单信息文本转换为歌单信息netMusicInfo
     * @param json
     * @return
     */
    public synchronized static NetMusicInfo changeJsonToInfo(String json)
    {

        NetMusicInfo netMusicInfo = new NetMusicInfo();

        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = jsonObject.getJSONArray("list");
            int size = jsonArray.length();

            for(int i = 0;i<size;i++)
            {
                JSONObject object = jsonArray.getJSONObject(i);
                MusicInfo musicInfo = new MusicInfo();
                musicInfo.title = object.getString("title");
                musicInfo.author = object.getString("author");
                musicInfo.duration = object.getInt("duration");
                musicInfo.file_size = object.getInt("file_size");
                musicInfo.file_path = object.getString("file_path");
                musicInfo.file_name = object.getString("file_name");
                musicInfo.id = object.getInt("id");
                netMusicInfo.musicInfos.add(musicInfo);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return netMusicInfo;
    }


}
