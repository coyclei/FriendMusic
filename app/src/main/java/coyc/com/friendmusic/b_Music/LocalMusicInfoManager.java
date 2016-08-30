package coyc.com.friendmusic.b_Music;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import coyc.com.friendmusic.info.MusicInfo;
import coyc.com.friendmusic.utils.l;

/**
 * Created by leipe on 2016/7/15.
 * 获取本地歌曲信息
 * 管理本地歌曲信息
 *
 * 权限：
 * <uses-permission android:name = "android.permission.MOUNT_UNMOUNT_FILESYSTEMS"></uses-permission>
 * <uses-permission android:name = "android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
 */
public class LocalMusicInfoManager {


    private static LocalMusicInfoManager instance;

    public static LocalMusicInfoManager getInstance() {
        if (instance == null) {
            synchronized (LocalMusicInfoManager.class) {
                if (instance == null) {
                    instance = new LocalMusicInfoManager();
                }
            }
        }
        return instance;
    }

    private LocalMusicInfoManager() {
    }

    /**
     * 在使用该类之前确保调用init方法
     * @param context
     */
    public void init(Context context)
    {
        mContext = context;
        initMusicInfo();
    }

    private Context mContext;

    /**
     * 本地歌曲信息列表
     */
    public ArrayList<MusicInfo> musicInfos = new ArrayList<MusicInfo>();


    public void refrshMusicInfo()
    {
        initMusicInfo();
    }


    /**
     * 获取本地音乐文件信息
     */
    public void initMusicInfo()
    {
        ContentResolver mResolver = mContext.getContentResolver();//connect to SQL
        Cursor cursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int counts = cursor.getCount();

        if(counts>0)
        {
            musicInfos.clear();
        }
        int k = 0;
        for(int i = 0;i<counts;i++)
        {
            String display_name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
            Log.d("coyc", "initMusicInfo:display_name" +display_name);
            String[] temp_authorAndTitle = obtainItem(display_name);
            String title = temp_authorAndTitle[1];
            String author = temp_authorAndTitle[0];
            if(title==null||author==null)
            {
                Log.d("coyc", "initMusicInfo: title==null||author==null " +display_name);
            }
            else
            {
                String file_path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                int file_size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                File file = new File(file_path);
                MusicInfo musicInfo = new MusicInfo();
                musicInfo.id = k;
                musicInfo.author = author;
                musicInfo.title = title;
                musicInfo.file_path = file_path;
                musicInfo.file_size = file_size;
                musicInfo.duration = duration;
                musicInfo.file_name = file.getName();
                Log.d("coyc", "initMusicInfo:file_name" +musicInfo.file_name);
                musicInfos.add(musicInfo);
                k++;
            }
            cursor.moveToNext();//to next song
        }
        cursor.close();

    }

    /**
     * 根据displayname 解析出更好的歌曲名以及作者名
     * @param str
     * @return
     */
    private String[] obtainItem(String str)
    {
        String singer = null,title = null;
        String separator = " - ";
        int middle = -1;
        for(int i = 0; i < str.length();i++)
        {
            if(str.charAt(i) == ' ')
            {
                int len = str.length() - i;
                if(len >= 3)
                {
                    String sub = str.substring(i,i+3);
                    if(sub.equals(separator))
                    {
                        singer = str.substring(0,i);
                        middle = i+3;
                    }
                }//end if len >= 3
            }//end if signer name

            if(str.charAt(i) == '.' && middle != -1)
            {
                int j = i;
                while( ++j < str.length())
                {
                    if( str.charAt(j) == '.')
                        break;
                }

                if(j != str.length())
                    continue;
                title = str.substring(middle, i);
                break;
            }//end if
        }//end for
        String[] temp = {singer,title};
        return temp;
    }//end for obtainItem



    public void logMusicInfo()
    {

        int size = musicInfos.size();
        for(int i = 0;i<size;i++)
        {
            l.l(i+" ############################");
            l.l(musicInfos.get(i).author);
            l.l(musicInfos.get(i).title);
            l.l(musicInfos.get(i).file_path);
        }
    }
}



