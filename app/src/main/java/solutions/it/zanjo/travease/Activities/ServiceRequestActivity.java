package solutions.it.zanjo.travease.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import solutions.it.zanjo.travease.Adapter.ServiceAdapter;
import solutions.it.zanjo.travease.Commons.Common;
import solutions.it.zanjo.travease.Model.Service;
import solutions.it.zanjo.travease.R;

public class ServiceRequestActivity extends AppCompatActivity {

    ServiceAdapter serviceAdapter;
    ListView serviceList;
    String service_id,res_id,guest_id;
    Button acceptBT;
    TextView guestTV,room_noTV,service_codeTV,service_typeTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_one);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        serviceAdapter=new ServiceAdapter(ServiceRequestActivity.this);
        Intent intent=getIntent();
        service_id=intent.getStringExtra("service_id");
        res_id=intent.getStringExtra("res_id");
        guest_id=intent.getStringExtra("guest_id");
        serviceList=(ListView)findViewById(R.id.listview);
        guestTV=(TextView)findViewById(R.id.guest_nameTV);
        room_noTV=(TextView)findViewById(R.id.room_noTV);
        service_codeTV=(TextView)findViewById(R.id.serviceCodeTV);
        service_typeTV=(TextView)findViewById(R.id.serviceTypeTV);
        Toast.makeText(ServiceRequestActivity.this, "Service_id, reservation_id & Guest_id  activity:"+service_id+", "+res_id+", "+guest_id, Toast.LENGTH_SHORT).show();
        TextView title= (TextView) toolbar.findViewById(R.id.TitleTV);
        title.setText("Service Request");
        ImageView backBT= (ImageView) toolbar.findViewById(R.id.backBT);
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        acceptBT=(Button)findViewById(R.id.acceptBT);
        acceptBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ServiceRequestActivity.this,ServiceRequest2Activity.class);
                intent.putExtra("type",service_typeTV.getText().toString());
                intent.putExtra("name",guestTV.getText().toString());
                intent.putExtra("room",room_noTV.getText().toString());
                intent.putExtra("code",service_codeTV.getText().toString());
                intent.putExtra("ser_id",service_id);
                startActivity(intent);
                finish();
            }
        });
        if (Common.isInternetOn())
        {
            new GetGuestDataTask().execute(Common.SERVER_URL+"api/all_work_guest_byid/"+guest_id);
            new GetReservationDataTask().execute(Common.SERVER_URL+"api/all_work_reservationbyid/"+res_id);
            new GetServiceDataTask().execute(Common.SERVER_URL+"api/service_request/"+service_id);
        }else  startActivity(new Intent(ServiceRequestActivity.this,NoInternetActivity.class));
    }

    class GetGuestDataTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ServiceRequestActivity.this);
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
                    String first_name=jObj.getString("first_name");
                    String last_name=jObj.getString("last_name");
                    guestTV.setText(first_name+" "+last_name);

                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ServiceRequestActivity.this, "Get Guest Data Unsuccessfully", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
    class GetServiceDataTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ServiceRequestActivity.this);
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
                        int id = jsonUser.getInt("id");
                        String item_name = jsonUser.getString("name");
                        String service_type = jsonUser.getString("type");
                        int displayOrder = jsonUser.getInt("displayOrder");
                        service_typeTV.setText(service_type);
                        Service service=new Service(item_name,displayOrder);
                        serviceAdapter.addItem(service);
                    }
                    serviceList.setAdapter(serviceAdapter);
                    serviceAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ServiceRequestActivity.this, "Get Service Data Unsuccessfully", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
    class GetReservationDataTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ServiceRequestActivity.this);
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
                    room_noTV.setText(jObj.getString("room"));
                    service_codeTV.setText(jObj.getString("code"));

                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ServiceRequestActivity.this, "Get Reservation Data Unsuccessfully", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
}
