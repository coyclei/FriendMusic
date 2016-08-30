package coyc.com.friendmusic.a_UI.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coyc.test_wifidirectmodule.wifidriect_kernel.MyWifiP2pDevice;

import java.util.ArrayList;

import coyc.com.friendmusic.R;

/**
 * Created by leipe on 2016/5/20.
 */
public class DeviceList_GroupAdapter extends BaseAdapter {

    private ArrayList<MyWifiP2pDevice> peers = new ArrayList<MyWifiP2pDevice>();
    private LayoutInflater inflater;
    private Context mContext;

    public DeviceList_GroupAdapter(ArrayList<MyWifiP2pDevice> ps , Context c)
    {
        peers = ps;
        mContext = c;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return peers.size();
    }

    @Override
    public Object getItem(int position) {
        return peers.get(position);
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
            convertView = inflater.inflate(R.layout.item_grouplist,parent,false);
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv_1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv_2);
            holder.tv3 = (TextView) convertView.findViewById(R.id.tv_3);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv1.setText(position+1+".  "+peers.get(position).name);
        holder.tv2.setText(" 点击获取歌单    ");
//        holder.tv2.setText("     "+peers.get(position).mac);
//        holder.tv3.setText("     "+peers.get(position).ip);
        holder.tv1.setTextColor(Color.WHITE);
        holder.tv2.setTextColor(Color.WHITE);
        holder.tv3.setTextColor(Color.WHITE);
        holder.tv3.setVisibility(View.GONE);
        return convertView;
    }


    public class ViewHolder{

        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
    }
}
