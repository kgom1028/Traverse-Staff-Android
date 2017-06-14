package solutions.it.zanjo.travease.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.util.ArrayList;

import solutions.it.zanjo.travease.Commons.Common;
import solutions.it.zanjo.travease.Model.Service;
import solutions.it.zanjo.travease.Model.StaffSelection;
import solutions.it.zanjo.travease.R;

public class EscalateActivity extends AppCompatActivity {

    ArrayList<StaffSelection> staffList;
    ArrayAdapter<StaffSelection> staffAdp;
    Spinner spEscalate;
    TextView guestTV,room_noTV,service_codeTV,service_typeTV;
    Button escalateBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escalate);
        escalateBT=(Button)findViewById(R.id.escalateBT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        staffList=new ArrayList<StaffSelection>();
        staffAdp=new ArrayAdapter<StaffSelection>(EscalateActivity.this,android.R.layout.simple_dropdown_item_1line,staffList);
        spEscalate=(Spinner)findViewById(R.id.escalateToSP);
        guestTV=(TextView)findViewById(R.id.guest_nameTV);
        room_noTV=(TextView)findViewById(R.id.room_noTV);
        service_codeTV=(TextView)findViewById(R.id.serviceCodeTV);
        service_typeTV=(TextView)findViewById(R.id.serviceTypeTV);
        TextView title= (TextView) toolbar.findViewById(R.id.TitleTV);
        title.setText("Escalate");
        ImageView backBT= (ImageView) toolbar.findViewById(R.id.backBT);

        // Get Data
        Intent intent=getIntent();
        service_typeTV.setText(intent.getStringExtra("type"));
        guestTV.setText(intent.getStringExtra("name"));
        room_noTV.setText(intent.getStringExtra("room"));
        service_codeTV.setText(intent.getStringExtra("code"));

        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        escalateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EscalateActivity.this,EscalateRequestActivity.class));
                finish();
            }
        });

        if (Common.isInternetOn())
        {
            new GetStaffDataTask().execute(Common.SERVER_URL+"api/all_name_staff");
        }else  startActivity(new Intent(EscalateActivity.this,NoInternetActivity.class));
    }

    class GetStaffDataTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(EscalateActivity.this);
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
                        String firstname = jsonUser.getString("firstname");
                        String lastname = jsonUser.getString("lastname");

                         StaffSelection staffSelection=new StaffSelection(id,firstname,lastname);
                         staffList.add(staffSelection);
                    }
                     spEscalate.setAdapter(staffAdp);
                    staffAdp.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(EscalateActivity.this, "Get Staff Data Unsuccessfully", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
}
