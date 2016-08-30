package coyc.com.friendmusic.info;

/**
 * Created by leipe on 2016/7/15.
 */
public class MusicInfo {

    public static final int PLAY_STATE_PLAYING = 0;
    public static final int PLAY_STATE_STOP = 1;
    public static final int PLAY_STATE_NOTHING = 2;
    public static final int PLAY_STATE_WAIT_DOWNLOAD = 3;
    public static final int PLAY_STATE_DOWNLOAD_OK = 4;


    public int id = 0;//在歌单中编号
    public String title = "";//歌曲名
    public String author = "";//作者名
    public int duration = 0;//播放时长
    public int file_size = 0;//文件大小
    public String file_path = "";//歌曲文件路径
    public String file_name = "";

    public boolean isSaveInLocal = false;

    public int play_state = PLAY_STATE_NOTHING;

}
