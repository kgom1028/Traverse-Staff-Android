package solutions.it.zanjo.travease.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import solutions.it.zanjo.travease.Commons.Common;
import solutions.it.zanjo.travease.R;

public class ProfileActivity extends AppCompatActivity {

    EditText firstnameET,lastnameET,emailET,passET,receptionET,housekeepET;
    Button saveBT;
    TextView profile_nameTV;
    String imagedata;
    int TAKE_PHOTO_CODE = 1;
    String uptype="dfsd";
    CircleImageView profile_img;
    Dialog dialog;
    TextView title;
    ImageView backBT,forwordBT;
    Button changepassBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firstnameET=(EditText)findViewById(R.id.firstnameET);
        lastnameET=(EditText)findViewById(R.id.lastET);
        emailET=(EditText)findViewById(R.id.emailET);
        passET=(EditText)findViewById(R.id.passET);
        receptionET=(EditText)findViewById(R.id.receptionET);
        housekeepET=(EditText)findViewById(R.id.hoousekeepET);
        saveBT=(Button)findViewById(R.id.saveBT);
        profile_img= (CircleImageView) findViewById(R.id.profile_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      title = (TextView) toolbar.findViewById(R.id.TitleTV);
        backBT= (ImageView) toolbar.findViewById(R.id.backBT);
        forwordBT= (ImageView) toolbar.findViewById(R.id.forwordBT);
        changepassBT= (Button) findViewById(R.id.changeBT);
        title.setText("Profile");
        forwordBT.setBackgroundResource(R.drawable.logout_img_b);
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        forwordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        changepassBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,ChangePasswordActivity.class));
            }
        });

        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup();
            }
        });

     saveBT.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             if (Common.isInternetOn())
             {
                 if (!validate()) {
                     onFailed();
                     return;
                 }

                 //new UpdateProfileTask().execute(Common.SERVER_URL+"Change_Password.php?email="+myPref.getData(ChangePasswordActivity.this,"email","")+"&password="+oldPassET.getText().toString()+"&newpassword="+newPassET.getText().toString());
                 // Toast.makeText(ChangePasswordActivity.this, "Email: "+myPref.getData(ChangePasswordActivity.this,"email",""), Toast.LENGTH_LONG).show();
             } else  startActivity(new Intent(ProfileActivity.this,NoInternetActivity.class));

         }
     });

    }
    public void onFailed() {
        Toast.makeText(ProfileActivity.this, "Profile Update failed", Toast.LENGTH_LONG).show();

    }

    public void popup()
    {
        try
        {

            dialog = new Dialog(ProfileActivity.this);
            dialog.setContentView(R.layout.reciept_popup);
            dialog.setTitle("Select Any Option");

            LinearLayout camera = (LinearLayout) dialog.findViewById(R.id.camera);
            LinearLayout gallery = (LinearLayout) dialog.findViewById(R.id.gallery);


            camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        uptype = "camera";
                        Reciept();
                        dialog.dismiss();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(ProfileActivity.this,"camera intent fire ===="+e,Toast.LENGTH_LONG).show();
                    }
                }
            });
            gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    uptype = "gellary";
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 1);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
        if(uptype.equals("camera")) {
            uptype = "dfsd";
            if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
                Bitmap bitmap;
                try {
                    Bundle extras = data.getExtras();
                     bitmap = (Bitmap) extras.get("data");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Uri uri = data.getData();
                     bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                imagedata = Base64.encodeToString(b, Base64.DEFAULT);
                profile_img.setImageBitmap(bitmap);
                //Toast.makeText(ProfileActivity.this, "OUT CAMERA: "+bitmap, Toast.LENGTH_SHORT).show();

            }
        }
        else if(uptype.equals("gellary"))
        {
            uptype = "cfdasf";
            if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
                // Let's read picked image data - its URI
                Uri pickedImage = data.getData();
                // Let's read picked image path using content resolver
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
                cursor.moveToFirst();
                String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                File file = new File(imagePath);
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                imagedata = Base64.encodeToString(b, Base64.DEFAULT);
                profile_img.setImageBitmap(bitmap);
                // At the end remember to close the cursor or you will end with the RuntimeException!
                cursor.close();
            }
        }
            dialog.dismiss();
        }
        catch (Exception e)
        {
            Toast.makeText(ProfileActivity.this,"camera intent result==="+e,Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }

    }
    public void Reciept()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PHOTO_CODE);

    }

    class UpdateProfileTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ProfileActivity.this);
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
                        //Toast.makeText(Pro.this, ""+msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(ChangePasswordActivity.this, "Change password Unsuccessfully", Toast.LENGTH_SHORT).show();
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
