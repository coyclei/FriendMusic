package coyc.com.friendmusic.a_UI.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import coyc.com.friendmusic.R;

/**
 * Created by leipe on 2016/5/20.
 */
public class DeviceList_PeersAdapter extends BaseAdapter {

    private ArrayList<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    private LayoutInflater inflater;
    private Context mContext;

    public DeviceList_PeersAdapter(ArrayList<WifiP2pDevice> ps , Context c)
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
            convertView = inflater.inflate(R.layout.item_peerslist,parent,false);
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv_1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv_2);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv1.setText(peers.get(position).deviceName);
//        holder.tv2.setText("     "+peers.get(position).deviceAddress);
        holder.tv2.setText("点击进行配对");
        holder.tv1.setTextColor(Color.WHITE);
        holder.tv2.setTextColor(Color.WHITE);
//        holder.tv2.setVisibility(View.GONE);
        return convertView;
    }


    public class ViewHolder{

        public TextView tv1;
        public TextView tv2;

    }
}
