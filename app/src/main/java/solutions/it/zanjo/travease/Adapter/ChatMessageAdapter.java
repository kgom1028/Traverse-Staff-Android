package solutions.it.zanjo.travease.Adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import solutions.it.zanjo.travease.Model.Message;
import solutions.it.zanjo.travease.R;

/**
 * Created by abc on 4/28/2017.
 */

public class ChatMessageAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<Message> l1;

    public ChatMessageAdapter(Context context, List<Message> l1) {
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

        final Message m = l1.get(position);
        View view=null;
        if(m.getMtype().equals("0"))
        {
            view = layoutInflater.inflate(R.layout.chat_from_item,parent,false);
        }
        else
        {
            view = layoutInflater.inflate(R.layout.chat_to_item,parent,false);
        }
        final TextView msg = (TextView) view.findViewById(R.id.msg);
        TextView time1 = (TextView) view.findViewById(R.id.time1);


        msg.setText(m.getMessage());
        time1.setText(m.getTime1());

        msg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(msg.getText());
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        return view;
    }
    public void RegisterMenu(TextView msg)
    {

    }

}

