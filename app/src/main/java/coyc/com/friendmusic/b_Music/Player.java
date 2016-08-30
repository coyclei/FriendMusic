package coyc.com.friendmusic.b_Music;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Message;

import coyc.com.friendmusic.a_UI.Activity.MainActivity;
import coyc.com.friendmusic.info.MusicInfo;
import coyc.com.friendmusic.utils.l;

public class Player {


	private static Player instance = null;

	public static final int Play_Mode_OtherPlay = 0;
	public static final int Play_Mode_SelfPlay = 1;

	public int play_mode = Play_Mode_SelfPlay;

	private Player()
	{
	}

	public static Player getInstance()
	{
		if(instance == null)
		{
			synchronized (Player.class)
			{
				if(instance == null)
				{
					instance = new Player();
				}
			}
		}
		return instance;
	}

	public void initPlayer(Context context)
	{
		mPlayer = new MediaPlayer();
		this.context = context;
		mPlayer.setLooping(true);
		OnCompletionListener();
	}

	
	private MediaPlayer mPlayer;
	private String path;
	private  Context context;
	private int id = 0;
	private int position;
	private boolean isplaying = false;


	public void OnCompletionListener()
	{
		mPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {

				l.l("11 onCompletion  MainActivity.current_playmusic.id"+MainActivity.current_playmusic.id);
				MainActivity.current_playmusic.play_state = MusicInfo.PLAY_STATE_NOTHING;
				MainActivity.mHandler.sendEmptyMessage(MainActivity.MusicPlayOver);
			}
		});
	}


	public void play(String path_)
	{
		path = path_;
		mPlayer.reset();
		try {
			mPlayer.setDataSource(path);
			mPlayer.prepare();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mPlayer.start();
		isplaying = true;
		MainActivity.isLocalPlay = true;
		SetTitle();
	}

	private void SetTitle() {
		MainActivity.mHandler.sendEmptyMessage(MainActivity.SetMusicTitle);
	}


	public void Stop()
	{
		position =mPlayer.getCurrentPosition();
		mPlayer.stop();
		isplaying = false;

		MainActivity.mHandler.sendEmptyMessage(MainActivity.StopPlay);
	}
	
	public int getCurrentPosition()
	{
		return mPlayer.getCurrentPosition();
	}
	public void Resume()
	{
		 if(position>0&&path!=null)  
	        {  
	                play(path);
	                isplaying = true;

	            mPlayer.seekTo(position);  
	            position=0;  
	        }
		MainActivity.isLocalPlay = true;
		MainActivity.mHandler.sendEmptyMessage(MainActivity.ResumePlay);
	}
	
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public void Destory()
	{
		mPlayer.release();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isIsplaying() {
		return isplaying;
	}

	public void setIsplaying(boolean isplaying) {
		this.isplaying = isplaying;
	}



	
}