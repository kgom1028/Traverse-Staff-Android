package solutions.it.zanjo.travease.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import solutions.it.zanjo.travease.Model.ChatList;
import solutions.it.zanjo.travease.R;

/**
 * Created by abc on 4/25/2017.
 */

public class ChatAdapter extends BaseAdapter {

    private Context _context =null;
    private ArrayList<ChatList> _addDatas = new ArrayList<ChatList>();


    public ChatAdapter(Context _context){

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
            convertView = inflater.inflate(R.layout.item_list_chat, null);

            addFriendHolder.nameTV = (TextView)convertView.findViewById(R.id.nameTV);
            addFriendHolder.timeTV = (TextView)convertView.findViewById(R.id.timeTV);
            addFriendHolder.room_noTV = (TextView)convertView.findViewById(R.id.room_noTV);


            convertView.setTag(addFriendHolder);
        }else {
            addFriendHolder = (AddFriendHolder)convertView.getTag();
        }

        final ChatList friend = _addDatas.get(position);

        addFriendHolder.nameTV.setText(friend.getName());
        addFriendHolder.timeTV.setText(friend.getTime());
        addFriendHolder.room_noTV.setText(friend.getRoom_no());


        return convertView;
    }

    public void addItem(ChatList entity){
        _addDatas.add(entity);
    }

    public void removeItem(ChatList entity) {
        _addDatas.remove(entity);
    }

    public void clearFriendData(){
        _addDatas.clear();
    }


    public class AddFriendHolder {


        public TextView nameTV,timeTV,room_noTV;

    }
}
