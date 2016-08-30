package coyc.com.friendmusic.a_UI.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import coyc.com.friendmusic.R;
import coyc.com.friendmusic.info.MusicInfo;

/**
 * Created by leipe on 2016/5/20.
 */
public class ImageAdapter extends BaseAdapter {

    public int[] res ;
    private LayoutInflater inflater;
    private Context mContext;

    public ImageAdapter(int[] res , Context c)
    {
        this.res = res;
        mContext = c;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return res.length;
    }

    @Override
    public Object getItem(int position) {
        return res[position];
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
            convertView = inflater.inflate(R.layout.item_image,parent,false);

            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv.setImageResource(res[position]);

        return convertView;
    }


    public class ViewHolder{

        public ImageView iv;

    }
}
