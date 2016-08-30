package coyc.com.friendmusic.a_UI.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import coyc.com.friendmusic.R;
import coyc.com.friendmusic.utils.SharePreferenceUtil;

public class LoginActivity extends Activity {


    private EditText mEt_name;

    private EditText mEt_lrc;
    private SharePreferenceUtil sharePreferenceUtil;

    private int[] res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initView();
    }

    private void initView() {
        mEt_name = (EditText) findViewById(R.id.et_name);
        mEt_lrc = (EditText) findViewById(R.id.et_love_lrc);
    }

    private void initData() {
        sharePreferenceUtil = new SharePreferenceUtil(getBaseContext(),"FriendMusic");

        res = new int[5];
        res[0] = R.mipmap.b;
        res[1] = R.mipmap.b1;
        res[2] = R.mipmap.b2;
        res[3] = R.mipmap.b3;
        res[4] = R.mipmap.b4;

    }

    public void OnClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_1:
                sharePreferenceUtil.setImg(1);
                toMainAcyivity();
                break;
            case R.id.iv_2:
                sharePreferenceUtil.setImg(2);
                toMainAcyivity();
                break;
            case R.id.iv_3:
                sharePreferenceUtil.setImg(3);
                toMainAcyivity();
                break;
            case R.id.iv_4:
                sharePreferenceUtil.setImg(4);
                toMainAcyivity();
                break;
            case R.id.iv_5:
                sharePreferenceUtil.setImg(5);
                toMainAcyivity();
                break;
            case R.id.iv_6:
                sharePreferenceUtil.setImg(6);
                toMainAcyivity();
                break;
        }
    }


    private void toMainAcyivity(){
        if(mEt_name.getText().toString().length()==0)
        {
            Toast.makeText(getBaseContext(),"请先输入您的音乐名",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mEt_lrc.getText().toString().length()==0)
        {
            sharePreferenceUtil.setLrc("");
        }else
        {
            sharePreferenceUtil.setLrc(mEt_lrc.getText().toString());
        }
        sharePreferenceUtil.setName(mEt_name.getText().toString());


        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
