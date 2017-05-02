package solutions.it.zanjo.travease.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import solutions.it.zanjo.travease.R;

/**
 * Created by abc on 4/25/2017.
 */

public class NotificationAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<String> l1;

    public NotificationAdapter(Context context, List<String> l1) {
        this.context = context;
        this.l1 = l1;
        layoutInflater =LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return l1.size();
    }

    @Override
    public Object getItem(int position) {
        return l1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.item_list,parent,false);
//        TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
//
//        tv1.setText(l1.get(position));

        return convertView;
    }
}

