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

public class ForgotPasswordActivity extends AppCompatActivity {

    Button resetBT;
    EditText emailET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailET= (EditText) findViewById(R.id.emailET);
        resetBT= (Button) findViewById(R.id.resetBT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView title= (TextView) toolbar.findViewById(R.id.TitleTV);
        title.setText("Forgot Password");
        ImageView backBT= (ImageView) toolbar.findViewById(R.id.backBT);
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resetBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isInternetOn())
                {
                    if (!validate()) {
                        onResetFailed();
                        return;
                    }
                    new ForgotTask().execute(Common.SERVER_URL+"forget_pass.php?email="+emailET.getText().toString());

                } else  startActivity(new Intent(ForgotPasswordActivity.this,NoInternetActivity.class));

            }
        });
    }
    public void onResetFailed() {
        Toast.makeText(ForgotPasswordActivity.this, "Reset failed", Toast.LENGTH_LONG).show();

    }
    class ForgotTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
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
                        String pass=jObj.getString("Check");
                        Toast.makeText(ForgotPasswordActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                        Toast.makeText(ForgotPasswordActivity.this, ""+pass, Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        Toast.makeText(ForgotPasswordActivity.this, "Invalid Email Id", Toast.LENGTH_SHORT).show();
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

        String email = emailET.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("enter a valid email address");
            valid = false;
        } else {
            emailET.setError(null);
        }

        return valid;
    }
}
