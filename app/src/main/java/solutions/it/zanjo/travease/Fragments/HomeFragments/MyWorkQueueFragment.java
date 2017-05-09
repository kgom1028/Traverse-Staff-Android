package solutions.it.zanjo.travease.Fragments.HomeFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import solutions.it.zanjo.travease.Adapter.HomeListAdapter;
import solutions.it.zanjo.travease.R;

public class MyWorkQueueFragment extends Fragment {

    ListView lv1;
    List<String> l5 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_work, container, false);
        lv1 = (ListView) view.findViewById(R.id.listview);

        l5.add("Select Latter");
        l5.add("A");
        l5.add("B");
//        HomeListAdapter spa;
//
//        spa = new HomeListAdapter(getActivity().getApplicationContext(), l5);
//        lv1.setAdapter(spa);
        return view;
    }
}
