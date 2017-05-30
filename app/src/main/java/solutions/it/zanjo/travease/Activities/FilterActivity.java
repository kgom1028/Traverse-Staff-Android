package solutions.it.zanjo.travease.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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

import solutions.it.zanjo.travease.Adapter.FilterDepartAdapter;
import solutions.it.zanjo.travease.Commons.Common;
import solutions.it.zanjo.travease.Fragments.HomeFragments.AllWorkQueueFragment;
import solutions.it.zanjo.travease.Model.Filter_Depart;
import solutions.it.zanjo.travease.R;

public class FilterActivity extends AppCompatActivity {

    FilterDepartAdapter filterDepartAdapter;
    ListView filter_depart_list;
    CheckBox newReqCB,assignCB,asignTootherCB,problemCB;
    TextView title;
    ImageView backBT,forwordBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        filter_depart_list=(ListView)findViewById(R.id.filter_depart_list);
        newReqCB=(CheckBox)findViewById(R.id.newReqCB);
        assignCB=(CheckBox)findViewById(R.id.assignCB);
        asignTootherCB=(CheckBox)findViewById(R.id.assignToOtherCB);
        problemCB=(CheckBox)findViewById(R.id.problemCB);
        filterDepartAdapter=new FilterDepartAdapter(FilterActivity.this);

        title = (TextView) toolbar.findViewById(R.id.TitleTV);
        backBT= (ImageView) toolbar.findViewById(R.id.backBT);
        forwordBT= (ImageView) toolbar.findViewById(R.id.forwordBT);
        forwordBT.setBackgroundResource(R.drawable.clear);
        title.setText("Filter");
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        forwordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newReqCB.setChecked(false);
                assignCB.setChecked(false);
                asignTootherCB.setChecked(false);
                problemCB.setChecked(false);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Common.isInternetOn())
        {
            new GetFilterDepartTask().execute(Common.SERVER_URL+"api/get_alldepartment");
        } else startActivity(new Intent(FilterActivity.this,NoInternetActivity.class));
    }

    class GetFilterDepartTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(FilterActivity.this);
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
                        String name = jsonUser.getString("name");

                        Filter_Depart filter_depart=new Filter_Depart(id,name);
                        filterDepartAdapter.addItem(filter_depart);
                    }
                    filter_depart_list.setAdapter(filterDepartAdapter);
                    filterDepartAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(FilterActivity.this, "Get Data Unsuccessfully", Toast.LENGTH_SHORT).show();
                }


            }
        }
    }
}
