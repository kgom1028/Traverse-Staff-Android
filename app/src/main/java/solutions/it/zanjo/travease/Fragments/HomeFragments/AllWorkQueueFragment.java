package solutions.it.zanjo.travease.Fragments.HomeFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import solutions.it.zanjo.travease.Activities.NoInternetActivity;
import solutions.it.zanjo.travease.Activities.ProfileActivity;
import solutions.it.zanjo.travease.Activities.ServiceRequestActivity;
import solutions.it.zanjo.travease.Adapter.HomeListAdapter;
import solutions.it.zanjo.travease.Commons.Common;
import solutions.it.zanjo.travease.Model.Home_Work;
import solutions.it.zanjo.travease.R;


public class AllWorkQueueFragment extends Fragment {

    ListView lv1;

    HomeListAdapter homeListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_work, container, false);
        lv1 = (ListView) view.findViewById(R.id.listview);

        homeListAdapter=new HomeListAdapter(getActivity());
        lv1.setAdapter(homeListAdapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), ServiceRequestActivity.class));
            }
        });
        if (Common.isInternetOn())
        {
            new AllWorkDataTask().execute(Common.SERVER_URL+"all_work.php");
        }else  startActivity(new Intent(getActivity(),NoInternetActivity.class));
        return view;
    }

    class AllWorkDataTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("\t\tPlease wait...");
            progressDialog.show();

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
                        JSONArray jsonArray = jObj.getJSONArray("Get All Work Successfully");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonUser = jsonArray.getJSONObject(i);
                            String Id=jsonUser.getString("Id");
                            String Viewwork=jsonUser.getString("Viewwork");
                            String Requeststatus=jsonUser.getString("Requeststatus");
                            String Time=jsonUser.getString("Time");
                            String Date=jsonUser.getString("Date");
                            String room_no=jsonUser.getString("room_no");

                            Home_Work home_work=new Home_Work(Id,Viewwork,Requeststatus,Time,Date,room_no);
                            homeListAdapter.addItem(home_work);
                        }
                        lv1.setAdapter(homeListAdapter);
                        homeListAdapter.notifyDataSetChanged();
                        //Toast.makeText(getActivity(), "Show Data", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(ProfileActivity.this, "Profile Update Unsuccessfully", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}
