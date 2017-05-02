package solutions.it.zanjo.travease.Activities;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import solutions.it.zanjo.travease.Adapter.ChatMessageAdapter;
import solutions.it.zanjo.travease.Model.Message;
import solutions.it.zanjo.travease.R;

public class ChatMessageActivity extends AppCompatActivity {

    ListView lv1;
    List<Message> l1 = new ArrayList<>();
    EditText msg;
    ImageView cembt;
    TextView chatbt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);
        lv1 = (ListView) findViewById(R.id.lv1);
        msg = (EditText) findViewById(R.id.msg);
        chatbt = (TextView) findViewById(R.id.chatbt);
        cembt = (ImageView) findViewById(R.id.cembt);

        Message m1 =new Message("0","hello","0","09:30 PM");
        Message m2 =new Message("0","hii","1","09:30 PM");
        Message m3 =new Message("0","how are you","0","09:30 PM");
        Message m4 =new Message("0","f9 and you","1","09:30 PM");
        Message m5 =new Message("0","i am also f9","0","09:30 PM");
        Message m6 =new Message("0","lorem ipsome","1","09:30 PM");
        Message m7 =new Message("0","is a dummy content","0","09:30 PM");
        Message m8 =new Message("0","sjdsdn","1","09:30 PM");
        Message m9 =new Message("0","sdjfhd","0","09:30 PM");

        l1.add(m1);
        l1.add(m2);
        l1.add(m3);
        l1.add(m4);
        l1.add(m5);
        l1.add(m6);
        l1.add(m7);
        l1.add(m8);
        l1.add(m9);

        ChatMessageAdapter na =new ChatMessageAdapter(ChatMessageActivity.this,l1);
        lv1.setAdapter(na);


        chatbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatBt();
            }
        });
        cembt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CemeraBt();
            }
        });
    }

    public void CemeraBt()
    {

    }
    public void ChatBt()
    {
        if(msg.getText().toString().length()==0)
        {
            msg.setError("Write Something.....");
        }
        else
        {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String time1 = dateFormat.format(cal.getTime());

            Message m = new Message("0",msg.getText().toString(),"0",time1);
            l1.add(m);
            ChatMessageAdapter na =new ChatMessageAdapter(ChatMessageActivity.this,l1);
            lv1.setAdapter(na);
            msg.setText("");
        }
    }
}

