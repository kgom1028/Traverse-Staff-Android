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
import solutions.it.zanjo.travease.Storage.MyPref;


public class AllWorkQueueFragment extends Fragment {

    ListView lv1;
    String room="",request_status="",timeandDate="",request_name="";
    int id=1;
    String service_id="",reservation_id="",guest_id="";
    HomeListAdapter homeListAdapter;
    MyPref myPref;
    String res_frag_id,service_frag_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_work, container, false);
        lv1 = (ListView) view.findViewById(R.id.listview);
           myPref=new MyPref();
              myPref.ClearID(getActivity());
//        res_frag_id=getArguments().getString("reservation_id");
//        service_frag_id=getArguments().getString("service_id");

        homeListAdapter=new HomeListAdapter(getActivity());
        lv1.setAdapter(homeListAdapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Home_Work home_work= (Home_Work) homeListAdapter.getItem(position);

                Intent intent=new Intent(getActivity(),ServiceRequestActivity.class);
                 intent.putExtra("service_id",home_work.getService_id());
                intent.putExtra("res_id",home_work.getReservation_id());
                intent.putExtra("guest_id",home_work.getGuest_id());
                Toast.makeText(getActivity(), "Service_id, reservation_id & Guest_id "+home_work.getService_id()+", "+home_work.getReservation_id()+", "+home_work.getGuest_id(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        if (Common.isInternetOn())
        {
            new AllWorkDataTask().execute(Common.SERVER_URL+"api/all_work");
        }else  startActivity(new Intent(getActivity(),NoInternetActivity.class));
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        Toast.makeText(getActivity(), "res_frag_id & service_frag_id"+myPref.getData(getActivity(),"res_id",0)+", "+myPref.getData(getActivity(),"ser_id",0), Toast.LENGTH_SHORT).show();
        if (myPref.getData(getActivity(),"res_id",0)!=0 && myPref.getData(getActivity(),"ser_id",0)!=0) {
            homeListAdapter.clearFriendData();
            new AllWorkDataTask().execute(Common.SERVER_URL+"api/all_work");
            new AllWorkReservationDataTask().execute(Common.SERVER_URL + "api/all_work_reservationbyid/" + myPref.getData(getActivity(),"res_id",0));
            new AllWorkServiceDataTask().execute(Common.SERVER_URL + "api/servicedata_id/" + myPref.getData(getActivity(),"ser_id",0));
        }
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

                httpCon.setRequestMethod("GET");
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

                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonUser = jsonArray.getJSONObject(i);
                        String id = jsonUser.getString("id");
                        guest_id = jsonUser.getString("guest_id");
                         reservation_id = jsonUser.getString("reservation_id");
                        String department_id = jsonUser.getString("department_id");
                         service_id= jsonUser.getString("service_id");
                        Toast.makeText(getActivity(), "S_id, r_id & G_id :"+service_id+", "+reservation_id+", "+guest_id, Toast.LENGTH_SHORT).show();
                        if (myPref.getData(getActivity(),"res_id",0)==0 && myPref.getData(getActivity(),"ser_id",0)==0) {
                        new AllWorkReservationDataTask().execute(Common.SERVER_URL+"api/all_work_reservationbyid/"+reservation_id);
                        new AllWorkServiceDataTask().execute(Common.SERVER_URL+"api/servicedata_id/"+service_id);
                    }}
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Get Data Unsuccessfully", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
    class AllWorkReservationDataTask extends AsyncTask<String,Void,String> {
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

                httpCon.setRequestMethod("GET");
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
                    room=jObj.getString("room");
                    request_status=jObj.getString("status");

                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Get Reservation Data Unsuccessfully", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
    class AllWorkServiceDataTask extends AsyncTask<String,Void,String> {
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

                httpCon.setRequestMethod("GET");
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

                    JSONArray jsonArray = new JSONArray(result);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonUser = jsonArray.getJSONObject(i);

                        id=jsonUser.getInt("id");
                        request_name=jsonUser.getString("name");

                        JSONObject jsonObject=jsonUser.getJSONObject("startTime");

                        timeandDate=jsonObject.getString("date");
                        String Id=String.valueOf(id);
                        Home_Work home_work=new Home_Work(Id,service_id,reservation_id,guest_id,request_name,request_status,timeandDate,timeandDate,room);
                        homeListAdapter.addItem(home_work);
                    }
                    lv1.setAdapter(homeListAdapter);
                    homeListAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Show Data", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Get Service Data Unsuccessfully", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
}
