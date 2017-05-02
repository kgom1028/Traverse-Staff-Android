package solutions.it.zanjo.travease.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import solutions.it.zanjo.travease.Adapter.NotificationAdapter;
import solutions.it.zanjo.travease.R;

public class NotificationFragment extends Fragment {

    ListView lv1;
    List<String> l5 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_notification,container,false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        lv1 = (ListView) view.findViewById(R.id.listview);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        TextView title= (TextView) toolbar.findViewById(R.id.TitleTV);
        title.setText("Notification");

        l5.add("Select Latter");
        l5.add("A");
        l5.add("B");
        NotificationAdapter spa;

        spa = new NotificationAdapter(getActivity().getApplicationContext(),l5);
        lv1.setAdapter(spa);
        return view;
    }
}
