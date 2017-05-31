package solutions.it.zanjo.travease.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import solutions.it.zanjo.travease.Activities.ServiceRequestActivity;
import solutions.it.zanjo.travease.Model.ChatList;
import solutions.it.zanjo.travease.Model.Service;
import solutions.it.zanjo.travease.R;

/**
 * Created by abc on 4/25/2017.
 */

public class ServiceAdapter extends BaseAdapter {

    private ServiceRequestActivity _context =null;
    private ArrayList<Service> _addDatas = new ArrayList<Service>();


    public ServiceAdapter(ServiceRequestActivity _context){

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
            convertView = inflater.inflate(R.layout.list_item_service, null);

            addFriendHolder.item_nameTV = (TextView)convertView.findViewById(R.id.item_nameTV);
            addFriendHolder.displayorderTV = (TextView)convertView.findViewById(R.id.display_orderTV);



            convertView.setTag(addFriendHolder);
        }else {
            addFriendHolder = (AddFriendHolder)convertView.getTag();
        }

        final Service friend = _addDatas.get(position);

        addFriendHolder.item_nameTV.setText(friend.getItem_name());
        addFriendHolder.displayorderTV.setText(friend.getDisplayorder()+"");



        return convertView;
    }

    public void addItem(Service entity){
        _addDatas.add(entity);
    }

    public void removeItem(Service entity) {
        _addDatas.remove(entity);
    }

    public void clearFriendData(){
        _addDatas.clear();
    }


    public class AddFriendHolder {


        public TextView item_nameTV,displayorderTV;

    }
}
