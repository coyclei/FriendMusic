package coyc.com.friendmusic.a_UI.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import coyc.com.friendmusic.R;
import coyc.com.friendmusic.info.MusicInfo;

/**
 * Created by leipe on 2016/5/20.
 */
public class MusicInfoAdapter extends BaseAdapter {

    public ArrayList<MusicInfo> info = new ArrayList<MusicInfo>();
    private LayoutInflater inflater;
    private Context mContext;

    public MusicInfoAdapter(ArrayList<MusicInfo> ps , Context c)
    {
        info = ps;
        mContext = c;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int position) {
        return info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_musiclist,parent,false);

            holder.tv0 = (TextView) convertView.findViewById(R.id.tv_0);
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv_1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv_2);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tv1.setText(info.get(position).title);
        holder.tv2.setText(info.get(position).author);
        holder.tv0.setText(position+1+"");

        switch (info.get(position).play_state)
        {
            case MusicInfo.PLAY_STATE_NOTHING:
                convertView.setBackgroundColor(Color.argb(127,0,0,0));
                if(info.get(position).isSaveInLocal)
                {
                    convertView.setBackgroundColor(Color.argb(147,0,0,0));
                }
                break;
            case MusicInfo.PLAY_STATE_PLAYING:
                convertView.setBackgroundColor(Color.argb(180,255,30,30));
                break;
            case MusicInfo.PLAY_STATE_STOP:
                convertView.setBackgroundColor(Color.argb(220,64,128,128));
                break;
            case MusicInfo.PLAY_STATE_WAIT_DOWNLOAD:
                convertView.setBackgroundColor(Color.argb(227,128,0,255));
                break;
            case MusicInfo.PLAY_STATE_DOWNLOAD_OK:
                convertView.setBackgroundColor(Color.argb(227,0,255,64));
                break;
            default:
                convertView.setBackgroundColor(Color.argb(90,0,0,0));
                break;

        }
//        if(info.get(position).isSaveInLocal)
//        {
//
//        }
//        convertView.setBackgroundColor(Color.argb(127,255,128,0));
        return convertView;
    }


    public class ViewHolder{

        public TextView tv0;
        public TextView tv1;
        public TextView tv2;

    }
}
