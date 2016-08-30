package coyc.com.friendmusic.utils;

import android.util.Log;

import java.util.ArrayList;

import coyc.com.friendmusic.info.MusicInfo;

/**
 * Created by leipe on 2016/7/15.
 */
public class l {

    public static void l(String msg)
    {
        Log.i("coyc",msg);
    }

    public static void logMusicInfo(ArrayList<MusicInfo> musicInfos)
    {

        int size = musicInfos.size();
        for(int i = 0;i<size;i++)
        {
            l.l(i+" ****************************");
            l.l(musicInfos.get(i).author);
            l.l(musicInfos.get(i).title);
            l.l(musicInfos.get(i).file_path);
        }
    }
}
