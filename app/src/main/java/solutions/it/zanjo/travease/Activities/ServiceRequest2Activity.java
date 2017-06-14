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

import solutions.it.zanjo.travease.Adapter.Service2Adapter;
import solutions.it.zanjo.travease.Adapter.ServiceAdapter;
import solutions.it.zanjo.travease.Commons.Common;
import solutions.it.zanjo.travease.Model.Service;
import solutions.it.zanjo.travease.R;

public class ServiceRequest2Activity extends AppCompatActivity {

    Service2Adapter serviceAdapter;
    ListView serviceList;
    String service_id;
     Button completeBT,escalateBT,reassignBT;
    TextView guestTV,room_noTV,service_codeTV,service_typeTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request2);

        serviceAdapter=new Service2Adapter(ServiceRequest2Activity.this);
        serviceList=(ListView)findViewById(R.id.listview);
        completeBT= (Button) findViewById(R.id.completeBT);
        escalateBT= (Button) findViewById(R.id.escalateBT);
        reassignBT= (Button) findViewById(R.id.reassignBT);
        guestTV=(TextView)findViewById(R.id.guest_nameTV);
        room_noTV=(TextView)findViewById(R.id.room_noTV);
        service_codeTV=(TextView)findViewById(R.id.serviceCodeTV);
        service_typeTV=(TextView)findViewById(R.id.serviceTypeTV);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get Data
        Intent intent=getIntent();
        service_typeTV.setText(intent.getStringExtra("type"));
        guestTV.setText(intent.getStringExtra("name"));
        room_noTV.setText(intent.getStringExtra("room"));
        service_codeTV.setText(intent.getStringExtra("code"));
        service_id=intent.getStringExtra("ser_id");

        Toast.makeText(ServiceRequest2Activity.this, "Service 2"+service_id, Toast.LENGTH_SHORT).show();

        TextView title= (TextView) toolbar.findViewById(R.id.TitleTV);
        title.setText("Service Request");
       ImageView backBT= (ImageView) toolbar.findViewById(R.id.backBT);
        ImageView forwordBT= (ImageView) toolbar.findViewById(R.id.forwordBT);
        forwordBT.setBackgroundResource(R.drawable.chat_service_icon);
        forwordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceRequest2Activity.this,ChatMessageActivity.class));

            }
        });

        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        completeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });
        escalateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ServiceRequest2Activity.this,EscalateActivity.class);
                intent.putExtra("type",service_typeTV.getText().toString());
                intent.putExtra("name",guestTV.getText().toString());
                intent.putExtra("room",room_noTV.getText().toString());
                intent.putExtra("code",service_codeTV.getText().toString());
                intent.putExtra("ser_id",service_id);
                startActivity(intent);
                finish();
            }
        });
        reassignBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceRequest2Activity.this,ReassignActivity.class));
                finish();

            }
        });
        if (Common.isInternetOn())
        {
            new GetServiceDataTask().execute(Common.SERVER_URL+"api/service_request/"+service_id);
        }else  startActivity(new Intent(ServiceRequest2Activity.this,NoInternetActivity.class));
    }

    class GetServiceDataTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ServiceRequest2Activity.this);
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
                    Toast.makeText(ServiceRequest2Activity.this, "Get Service Data Unsuccessfully", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
}
