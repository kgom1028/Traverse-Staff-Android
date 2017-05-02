package solutions.it.zanjo.travease.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText oldPassET,newPassET,confirmPassET;
    Button saveBT;
    TextView title;
    ImageView backBT;
    MyPref myPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        myPref=new MyPref();
        oldPassET=(EditText)findViewById(R.id.oldPassET);
        newPassET=(EditText)findViewById(R.id.newPassET);
        confirmPassET=(EditText)findViewById(R.id.confmPassET);
        saveBT=(Button)findViewById(R.id.saveBT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (TextView) toolbar.findViewById(R.id.TitleTV);
        backBT= (ImageView) toolbar.findViewById(R.id.backBT);
        title.setText("Change Password");
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       // Toast.makeText(ChangePasswordActivity.this, "Email: "+myPref.getData(ChangePasswordActivity.this,"email",""), Toast.LENGTH_LONG).show();

        saveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isInternetOn())
                {
                    if (!validate()) {
                        onResetFailed();
                        return;
                    }

                    new ChangePassTask().execute(Common.SERVER_URL+"Change_Password.php?email="+myPref.getData(ChangePasswordActivity.this,"email","")+"&password="+oldPassET.getText().toString()+"&newpassword="+newPassET.getText().toString());
                   // Toast.makeText(ChangePasswordActivity.this, "Email: "+myPref.getData(ChangePasswordActivity.this,"email",""), Toast.LENGTH_LONG).show();
                } else  startActivity(new Intent(ChangePasswordActivity.this,NoInternetActivity.class));

            }
        });
    }
    public void onResetFailed() {
        Toast.makeText(ChangePasswordActivity.this, "Change Password failed", Toast.LENGTH_LONG).show();

    }
    class ChangePassTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ChangePasswordActivity.this);
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
                        String email=jObj.getString("email");
                        Toast.makeText(ChangePasswordActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(ForgotPasswordActivity.this, ""+pass, Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        Toast.makeText(ChangePasswordActivity.this, "Change password Unsuccessfully", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    public boolean validate() {
        boolean valid = true;

        String Oldpass = oldPassET.getText().toString();
        String NewPass = newPassET.getText().toString();
        String ConfirmPass = confirmPassET.getText().toString();
        if (Oldpass.isEmpty() || Oldpass.length() < 4 || Oldpass.length() > 10) {
            oldPassET.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            oldPassET.setError(null);
        }
        if (NewPass.isEmpty() || NewPass.length() < 4 || NewPass.length() > 10) {
            newPassET.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            newPassET.setError(null);
        }
        if (ConfirmPass.isEmpty() || ConfirmPass.length() < 4 || ConfirmPass.length() > 10) {
            confirmPassET.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            confirmPassET.setError(null);
        }

          if (!(newPassET.getText().toString().equals(confirmPassET.getText().toString()))){
              confirmPassET.setError("Please enter correct password");
            valid= false;
        }
        else {


        }

        return valid;
    }
}
