package solutions.it.zanjo.travease.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import solutions.it.zanjo.travease.Activities.ChatMessageActivity;
import solutions.it.zanjo.travease.Adapter.ChatAdapter;
import solutions.it.zanjo.travease.Adapter.HomeListAdapter;
import solutions.it.zanjo.travease.R;

/**
 * Created by abc on 4/24/2017.
 */

public class ChatFragment extends Fragment {

    ListView lv1;
    List<String> l5 = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        TextView title= (TextView) toolbar.findViewById(R.id.TitleTV);
        title.setText("Chat");

        lv1 = (ListView) view.findViewById(R.id.listview);

        l5.add("Select Latter");
        l5.add("A");
        l5.add("B");
        l5.add("C");
        l5.add("D");
        l5.add("E");
        ChatAdapter spa;

        spa = new ChatAdapter(getActivity().getApplicationContext(), l5);
        lv1.setAdapter(spa);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), ChatMessageActivity.class));
            }
        });

        return view;
    }
}
