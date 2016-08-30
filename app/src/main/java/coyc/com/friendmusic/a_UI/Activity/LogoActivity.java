package coyc.com.friendmusic.a_UI.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import coyc.com.friendmusic.R;
import coyc.com.friendmusic.utils.PreferenceUtils;
import coyc.com.friendmusic.utils.SharePreferenceUtil;

public class LogoActivity extends Activity {

    private SharePreferenceUtil sharePreferenceUtil;
    private boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        initData();

        initView();

        CheckIsLogin();

        toNextActivity();
    }

    private void toNextActivity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isLogin)
                {
                    Intent intent = new Intent(LogoActivity.this,MainActivity.class);
                    startActivity(intent);
                }else
                {
                    Intent intent = new Intent(LogoActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                LogoActivity.this.finish();
            }
        }).start();
    }
    private void CheckIsLogin() {

        if(sharePreferenceUtil.getName().length()==0)
        {
            isLogin = false;
        }else
        {
            isLogin = true;
        }
    }

    private void initView() {

    }

    private void initData() {
        sharePreferenceUtil = new SharePreferenceUtil(getBaseContext(),"FriendMusic");
    }
}
