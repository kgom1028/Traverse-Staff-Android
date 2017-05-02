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
import android.widget.TextView;

import solutions.it.zanjo.travease.Activities.AboutHotelActivity;
import solutions.it.zanjo.travease.Activities.HowWorkActivity;
import solutions.it.zanjo.travease.Activities.LoginActivity;
import solutions.it.zanjo.travease.Activities.PrivacyPolicyActivity;
import solutions.it.zanjo.travease.Activities.ReportProblemActivity;
import solutions.it.zanjo.travease.Activities.TermsConditionActivity;
import solutions.it.zanjo.travease.R;
import solutions.it.zanjo.travease.Storage.MyPref;


public class SettingsFragment extends Fragment {
    MyPref myPref;
    TextView howWorkTV,reportTV,PrivacyTV,aboutHotelTV,termCOnditionTV,LogoutTV;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_setting,container,false);
        myPref=new MyPref();
        howWorkTV= (TextView) view.findViewById(R.id.howWorkTV);
        reportTV= (TextView) view.findViewById(R.id.reportProblemTV);
        PrivacyTV= (TextView) view.findViewById(R.id.privacyPolicyTV);
        aboutHotelTV= (TextView) view.findViewById(R.id.aboutHotelTV);
        termCOnditionTV= (TextView) view.findViewById(R.id.TermConditionTV);
        LogoutTV= (TextView) view.findViewById(R.id.logoutTV);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        TextView title= (TextView) toolbar.findViewById(R.id.TitleTV);
        title.setText("Settings");

        howWorkTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HowWorkActivity.class));
            }
        });
        reportTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ReportProblemActivity.class));
            }
        });
        PrivacyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PrivacyPolicyActivity.class));
            }
        });
        aboutHotelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutHotelActivity.class));
            }
        });
        termCOnditionTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TermsConditionActivity.class));
            }
        });
        LogoutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPref.Clear(getActivity());
                getActivity().finish();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        return view;
    }

}
