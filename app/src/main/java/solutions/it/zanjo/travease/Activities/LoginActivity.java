package solutions.it.zanjo.travease.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import solutions.it.zanjo.travease.Commons.Common;
import solutions.it.zanjo.travease.R;

public class LoginActivity extends AppCompatActivity {

    Button loginBT;
    TextView forgotTV;
    EditText emailET,passET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBT= (Button) findViewById(R.id.loginBT);
        forgotTV= (TextView) findViewById(R.id.forgotTV);
        emailET= (EditText) findViewById(R.id.email_idET);
        passET= (EditText) findViewById(R.id.passET);

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isInternetOn())
                {
                    if (!validate()) {
                        onLoginFailed();
                        return;
                    }
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                } else  startActivity(new Intent(LoginActivity.this,NoInternetActivity.class));

            }
        });

        forgotTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });
    }

    class LoginTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
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
                    Log.e("Status", status);
                    JSONArray jsonArray=jObj.getJSONArray("continent");

                    for (int i=0; i< jsonArray.length(); i++) {
                        JSONObject Obj = jsonArray.getJSONObject(i);
                        String status1 = Obj.getString("status");
                     }
                     progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public void onLoginFailed() {
        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();

    }

    public boolean validate() {
        boolean valid = true;

        String email = emailET.getText().toString();
        String password = passET.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("enter a valid email address");
            valid = false;
        } else {
            emailET.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passET.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passET.setError(null);
        }

        return valid;
    }

}
