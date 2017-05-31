package solutions.it.zanjo.travease.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import solutions.it.zanjo.travease.Activities.FilterActivity;
import solutions.it.zanjo.travease.Model.ChatList;
import solutions.it.zanjo.travease.Model.Filter_Depart;
import solutions.it.zanjo.travease.R;

/**
 * Created by abc on 4/25/2017.
 */

public class FilterDepartAdapter extends BaseAdapter {

    Integer selected_position = -1;
    private FilterActivity _context =null;
    private ArrayList<Filter_Depart> _addDatas = new ArrayList<Filter_Depart>();


    public FilterDepartAdapter(FilterActivity _context){

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
    public View getView(final int position, View convertView, ViewGroup parent){

        AddFriendHolder addFriendHolder;

        if(convertView == null){
            addFriendHolder = new AddFriendHolder();

            LayoutInflater inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_filter_depart, null);

            addFriendHolder.nameTV = (TextView)convertView.findViewById(R.id.depart_filter_name);
            addFriendHolder.filter_depart_check=(CheckBox)convertView.findViewById(R.id.depart_filterCB);

            convertView.setTag(addFriendHolder);
        }else {
            addFriendHolder = (AddFriendHolder)convertView.getTag();
        }

        final Filter_Depart friend = _addDatas.get(position);

        addFriendHolder.nameTV.setText(friend.getName());

        addFriendHolder.filter_depart_check.setChecked(position==selected_position);
/*
          if (addFriendHolder.filter_depart_check.isChecked())
          {
              _context.Check(friend.getId());
          }*/
        addFriendHolder.filter_depart_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    selected_position =  position;
                    _context.Check(friend.getId());
                }
                else{
                    selected_position = -1;
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public void addItem(Filter_Depart entity){
        _addDatas.add(entity);
    }

    public void removeItem(Filter_Depart entity) {
        _addDatas.remove(entity);
    }

    public void clearFriendData(){
        _addDatas.clear();
    }


    public void Check()
    {}

    public class AddFriendHolder {


        public TextView nameTV;
        public CheckBox filter_depart_check;
    }
}
