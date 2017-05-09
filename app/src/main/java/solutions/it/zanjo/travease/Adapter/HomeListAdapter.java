package solutions.it.zanjo.travease.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import solutions.it.zanjo.travease.Model.Home_Work;
import solutions.it.zanjo.travease.R;

/**
 * Created by abc on 4/25/2017.
 */

public class HomeListAdapter extends BaseAdapter {

    private Context _context =null;
    private ArrayList<Home_Work> _addDatas = new ArrayList<Home_Work>();


    public HomeListAdapter(Context _context){

        super();
        this._context = _context;

    }

    @Override
    public int getCount(){

        return _addDatas.size();
    }

    @Override
    public Object getItem(int position){

        return _addDatas.get(position);
    }

    @Override
    public long getItemId(int position){

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        AddFriendHolder addFriendHolder;

        if(convertView == null){
            addFriendHolder = new AddFriendHolder();

            LayoutInflater inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_home, null);

            addFriendHolder.view_workTV = (TextView)convertView.findViewById(R.id.view_workTV);
            addFriendHolder.request_statusTV = (TextView)convertView.findViewById(R.id.request_statusTV);
            addFriendHolder.timeTV = (TextView)convertView.findViewById(R.id.timeTV);
            addFriendHolder.dateTV = (TextView)convertView.findViewById(R.id.dateTV);
            addFriendHolder.room_noTV = (TextView)convertView.findViewById(R.id.room_noTV);


            convertView.setTag(addFriendHolder);
        }else {
            addFriendHolder = (AddFriendHolder)convertView.getTag();
        }

        final Home_Work friend = _addDatas.get(position);

        addFriendHolder.view_workTV.setText(friend.getView_work());
        addFriendHolder.request_statusTV.setText(friend.getRequest_status());
        addFriendHolder.timeTV.setText(friend.getTime());
        addFriendHolder.dateTV.setText(friend.getDate());
        addFriendHolder.room_noTV.setText(friend.getRoom_no());


        return convertView;
    }

    public void addItem(Home_Work entity){
        _addDatas.add(entity);
    }

    public void removeItem(Home_Work entity) {
        _addDatas.remove(entity);
    }

    public void clearFriendData(){
        _addDatas.clear();
    }


    public class AddFriendHolder {


        public TextView view_workTV,request_statusTV,timeTV,dateTV,room_noTV;

    }
}
