package solutions.it.zanjo.travease.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
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
import solutions.it.zanjo.travease.Storage.MyPref;

public class LoginActivity extends AppCompatActivity {

    MyPref myPref;
    Button loginBT;
    TextView forgotTV;
    EditText emailET,passET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myPref=new MyPref();
        loginBT= (Button) findViewById(R.id.loginBT);
        forgotTV= (TextView) findViewById(R.id.forgotTV);
        emailET= (EditText) findViewById(R.id.email_idET);
        passET= (EditText) findViewById(R.id.passET);
        hasPermissions(this, Common.PERMISSIONS);
        String str = myPref.getData(getApplicationContext(),"email","null");
        if(str.equals("null"))
        {

        }
        else
        {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isInternetOn())
                {
                    if (!validate()) {
                        onLoginFailed();
                        return;
                    }
                    myPref.saveData(getApplicationContext(),"email",emailET.getText().toString());
                    myPref.saveData(getApplicationContext(),"pass",passET.getText().toString());

                        new LoginTask().execute(Common.SERVER_URL+"login.php?email="+emailET.getText().toString()+"&password="+passET.getText().toString());

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

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(LoginActivity.this, Common.PERMISSIONS, Common.MY_PEQUEST_CODE);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1 && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == Common.MY_PEQUEST_CODE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


        }
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
                     if (status.equals("true"))
                     {
                         String msg=jObj.getString("message");
                         String email=jObj.getString("Email");
                         String pass=jObj.getString("Password");
                         Toast.makeText(LoginActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                         finish();
                     }
                     else {
                         onLoginFailed();
                     }
                 /*   JSONArray jsonArray=jObj.getJSONArray("continent");

                    for (int i=0; i< jsonArray.length(); i++) {
                        JSONObject Obj = jsonArray.getJSONObject(i);
                        String status1 = Obj.getString("status");
                     }*/
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
