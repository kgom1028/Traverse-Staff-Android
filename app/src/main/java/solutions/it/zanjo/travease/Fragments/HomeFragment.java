package solutions.it.zanjo.travease.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import solutions.it.zanjo.travease.Activities.FilterActivity;
import solutions.it.zanjo.travease.Activities.NoInternetActivity;
import solutions.it.zanjo.travease.Activities.ProfileActivity;
import solutions.it.zanjo.travease.Adapter.HomeListAdapter;
import solutions.it.zanjo.travease.Adapter.NotificationAdapter;
import solutions.it.zanjo.travease.Commons.Common;
import solutions.it.zanjo.travease.Fragments.HomeFragments.AllWorkQueueFragment;
import solutions.it.zanjo.travease.Fragments.HomeFragments.MyWorkQueueFragment;
import solutions.it.zanjo.travease.R;
import solutions.it.zanjo.travease.Storage.MyPref;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    String service_id="",reservation_id="";
    TextView title;
    ImageView profileBT, filterBT;
    Button allWorkBT, myWorkBT;
  MyPref myPref;
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
       myPref=new MyPref();
        profileBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });
        filterBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), FilterActivity.class),1);
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
        if (Common.isInternetOn())
        {
            new GetProfileDataTask().execute(Common.SERVER_URL+"api/getProfileData.php?email="+myPref.getData(getActivity(),"email",""));
            // Toast.makeText(ChangePasswordActivity.this, "Email: "+myPref.getData(ChangePasswordActivity.this,"email",""), Toast.LENGTH_LONG).show();
        } else  startActivity(new Intent(getActivity(),NoInternetActivity.class));

        displayView(0);
        return view;
    }

    public void displayView(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new AllWorkQueueFragment();
                Bundle bundle = new Bundle();
                Toast.makeText(getActivity(), "Fragment"+reservation_id+", "+service_id, Toast.LENGTH_SHORT).show();
                bundle.putString("reservation_id",reservation_id);
                bundle.putString("service_id",service_id);
                fragment.setArguments(bundle);
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

    class GetProfileDataTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("\t\tPlease wait...");
            //progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String strUrl = params[0];
            String result = "";


            try {
                URL url = new URL(strUrl);
                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

                httpCon.setRequestMethod("POST");
                httpCon.connect();

                int respCode = httpCon.getResponseCode();
                if (respCode == HttpURLConnection.HTTP_OK) {
                    InputStream is = httpCon.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr);

                    //get all lines of servlet o/p
                    while (true) {
                        String str = reader.readLine();
                        if (str == null)
                            break;
                        result = result + str;
                    }
                }

            } catch (Exception ex) {
                Log.e("http error", ex.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.e("Test", result);

            if (result != null) {
                try {

                    JSONObject jObj = new JSONObject(result);
                    String status=jObj.getString("status");
                    if (status.equals("true"))
                    {
                        //String msg=jObj.getString("message");
//                        String user_id=jObj.getString("user_id");
//                        emailET.setText(jObj.getString("user_email"));
                        String  img_path=jObj.getString("image_path");
                        String  firstnameET=jObj.getString("first_name");
                        String lastnameET=jObj.getString("last_name");
                        title.setText(firstnameET+" "+lastnameET);
//                        housekeepET.setText(jObj.getString("house_keeping"));
//                        receptionET.setText(jObj.getString("reception"));

                        Picasso.with(getActivity())
                                .load(img_path)
                                .into(profileBT);
                        //Toast.makeText(ProfileActivity.this, "Get Profile Data", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(ProfileActivity.this, "Profile Update Unsuccessfully", Toast.LENGTH_SHORT).show();
                    }
                    //progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode==RESULT_OK && data!=null)
        {
            reservation_id=data.getStringExtra("reservation_id");
            service_id=data.getStringExtra("service_id");
            Toast.makeText(getActivity(), "reservation_id & service_id"+reservation_id+", "+service_id, Toast.LENGTH_SHORT).show();
        }

    }
}