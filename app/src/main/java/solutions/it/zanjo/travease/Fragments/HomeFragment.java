package solutions.it.zanjo.travease.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import solutions.it.zanjo.travease.Activities.FilterActivity;
import solutions.it.zanjo.travease.Activities.ProfileActivity;
import solutions.it.zanjo.travease.Adapter.HomeListAdapter;
import solutions.it.zanjo.travease.Adapter.NotificationAdapter;
import solutions.it.zanjo.travease.Fragments.HomeFragments.AllWorkQueueFragment;
import solutions.it.zanjo.travease.Fragments.HomeFragments.MyWorkQueueFragment;
import solutions.it.zanjo.travease.R;

public class HomeFragment extends Fragment {



    TextView title;
    ImageView profileBT, filterBT;
    Button allWorkBT, myWorkBT;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.TitleTV);
        profileBT = (ImageView) toolbar.findViewById(R.id.profileBT);
        filterBT = (ImageView) toolbar.findViewById(R.id.filterBT);
        allWorkBT = (Button) view.findViewById(R.id.all_work_BT);
        myWorkBT = (Button) view.findViewById(R.id.my_work_BT);
        title.setText("No Name");

        profileBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
        filterBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FilterActivity.class));
            }
        });
        allWorkBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allWorkBT.setBackgroundResource(R.drawable.b01);
                myWorkBT.setBackgroundResource(R.drawable.w02);
                allWorkBT.setTextColor(getResources().getColor(R.color.white));
                myWorkBT.setTextColor(getResources().getColor(R.color.Line_Color));
                displayView(0);
            }
        });
        myWorkBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allWorkBT.setBackgroundResource(R.drawable.w01);
                myWorkBT.setBackgroundResource(R.drawable.b02);
                allWorkBT.setTextColor(getResources().getColor(R.color.Line_Color));
                myWorkBT.setTextColor(getResources().getColor(R.color.white));
                displayView(1);
            }
        });

       displayView(0);
        return view;
    }

    public void displayView(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new AllWorkQueueFragment();
                break;
            case 1:
                fragment = new MyWorkQueueFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame2, fragment);
            fragmentTransaction.commit();
        }

    }
}