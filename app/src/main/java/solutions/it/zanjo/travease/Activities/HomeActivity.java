package solutions.it.zanjo.travease.Activities;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import solutions.it.zanjo.travease.Fragments.ChatFragment;
import solutions.it.zanjo.travease.Fragments.HomeFragment;
import solutions.it.zanjo.travease.Fragments.NotificationFragment;
import solutions.it.zanjo.travease.Fragments.SettingsFragment;
import solutions.it.zanjo.travease.R;

public class HomeActivity extends AppCompatActivity {

    ImageButton homeIB,chatIB,settingIB,notifyIB;
    TextView homeTV,chatTV,settingTV,notifyTV;
    View homeV,chatV,settingV,notifyV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        homeIB= (ImageButton) findViewById(R.id.homeIB);
        chatIB= (ImageButton) findViewById(R.id.chatIB);
        settingIB= (ImageButton) findViewById(R.id.settingIB);
        notifyIB= (ImageButton) findViewById(R.id.notifyIB);



        homeTV= (TextView) findViewById(R.id.homeTV);
        chatTV= (TextView) findViewById(R.id.chatTV);
        settingTV= (TextView) findViewById(R.id.settingTV);
        notifyTV= (TextView) findViewById(R.id.notifyTV);

        homeV=findViewById(R.id.homeV);
        chatV=findViewById(R.id.chatV);
        settingV=findViewById(R.id.settingV);
        notifyV=findViewById(R.id.notifyV);
        displayView(0);
    }
    public void Home(View view)
    {
        homeIB.setBackgroundResource(R.drawable.home_img_b);
        homeTV.setTextColor(Color.parseColor("#24adc5"));
        homeV.setVisibility(View.VISIBLE);

        chatIB.setBackgroundResource(R.drawable.chat_img_g);
        chatTV.setTextColor(getResources().getColor(R.color.dark_gry));
        chatV.setVisibility(View.INVISIBLE);

        settingIB.setBackgroundResource(R.drawable.setting_new_g);
        settingTV.setTextColor(getResources().getColor(R.color.dark_gry));
        settingV.setVisibility(View.INVISIBLE);

        notifyIB.setBackgroundResource(R.drawable.notify_new_g);
        notifyTV.setTextColor(getResources().getColor(R.color.dark_gry));
        notifyV.setVisibility(View.INVISIBLE);

        displayView(0);
    }

    public void Chat(View view)
    {
        homeIB.setBackgroundResource(R.drawable.home_img_g);
        homeTV.setTextColor(getResources().getColor(R.color.dark_gry));
        homeV.setVisibility(View.INVISIBLE);

        chatIB.setBackgroundResource(R.drawable.chat_img_b);
        chatTV.setTextColor(Color.parseColor("#24adc5"));
        chatV.setVisibility(View.VISIBLE);

        settingIB.setBackgroundResource(R.drawable.setting_new_g);
        settingTV.setTextColor(getResources().getColor(R.color.dark_gry));
        settingV.setVisibility(View.INVISIBLE);

        notifyIB.setBackgroundResource(R.drawable.notify_new_g);
        notifyTV.setTextColor(getResources().getColor(R.color.dark_gry));
        notifyV.setVisibility(View.INVISIBLE);

        displayView(1);
    }


    public void Settings(View view)
    {
        homeIB.setBackgroundResource(R.drawable.home_img_g);
        homeTV.setTextColor(getResources().getColor(R.color.dark_gry));
        homeV.setVisibility(View.INVISIBLE);

        chatIB.setBackgroundResource(R.drawable.chat_img_g);
        chatTV.setTextColor(getResources().getColor(R.color.dark_gry));
        chatV.setVisibility(View.INVISIBLE);

        settingIB.setBackgroundResource(R.drawable.setting_img_b);
        settingTV.setTextColor(Color.parseColor("#24adc5"));
        settingV.setVisibility(View.VISIBLE);

        notifyIB.setBackgroundResource(R.drawable.notify_new_g);
        notifyTV.setTextColor(getResources().getColor(R.color.dark_gry));
        notifyV.setVisibility(View.INVISIBLE);


        displayView(2);
    }

    public void Notification(View view)
    {
        homeIB.setBackgroundResource(R.drawable.home_img_g);
        homeTV.setTextColor(getResources().getColor(R.color.dark_gry));
        homeV.setVisibility(View.INVISIBLE);

        chatIB.setBackgroundResource(R.drawable.chat_img_g);
        chatTV.setTextColor(getResources().getColor(R.color.dark_gry));
        chatV.setVisibility(View.INVISIBLE);

        settingIB.setBackgroundResource(R.drawable.setting_new_g);
        settingTV.setTextColor(getResources().getColor(R.color.dark_gry));
        settingV.setVisibility(View.INVISIBLE);

        notifyIB.setBackgroundResource(R.drawable.notify_new_b);
        notifyTV.setTextColor(Color.parseColor("#24adc5"));
        notifyV.setVisibility(View.VISIBLE);

        displayView(3);
    }

    public void displayView(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                 fragment = new ChatFragment();
                break;
            case 2:
                fragment = new SettingsFragment();
                break;
            case 3:
                fragment=new NotificationFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame1, fragment);
            fragmentTransaction.commit();
        }

    }
}
