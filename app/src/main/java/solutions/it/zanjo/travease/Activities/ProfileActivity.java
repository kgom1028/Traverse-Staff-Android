package solutions.it.zanjo.travease.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import solutions.it.zanjo.travease.Commons.Common;
import solutions.it.zanjo.travease.R;
import solutions.it.zanjo.travease.Storage.MyPref;

public class ProfileActivity extends AppCompatActivity {

    MyPref myPref;
    //upload image
    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="";

    private String KEY_IMAGE = "image";


    EditText firstnameET,lastnameET,emailET,passET,receptionET,housekeepET;
    Button saveBT;
    TextView profile_nameTV;
    String imagedata;
    int TAKE_PHOTO_CODE = 1;
    String uptype="dfsd";
    CircleImageView profile_img;
    Dialog dialog;
    TextView title;
    String imagePath="";
    String img_path="";
    ImageView backBT,forwordBT;
    Button changepassBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        myPref=new MyPref();
        UPLOAD_URL="http://zanjo.io/projects/Traverse_api/update_image.php?email="+myPref.getData(ProfileActivity.this,"email","");
        firstnameET=(EditText)findViewById(R.id.firstnameET);
        lastnameET=(EditText)findViewById(R.id.lastET);
        emailET=(EditText)findViewById(R.id.emailET);
        passET=(EditText)findViewById(R.id.passET);
        receptionET=(EditText)findViewById(R.id.receptionET);
        housekeepET=(EditText)findViewById(R.id.hoousekeepET);
        profile_nameTV=(TextView)findViewById(R.id.profile_nameTV);
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

        if (Common.isInternetOn())
        {
            new GetProfileDataTask().execute(Common.SERVER_URL+"getProfileData.php?email="+myPref.getData(ProfileActivity.this,"email",""));
            // Toast.makeText(ChangePasswordActivity.this, "Email: "+myPref.getData(ChangePasswordActivity.this,"email",""), Toast.LENGTH_LONG).show();
        } else  startActivity(new Intent(ProfileActivity.this,NoInternetActivity.class));



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
       profile_nameTV.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               uploadImage();
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

                 new UpdateProfileTask().execute(Common.SERVER_URL+"update_profile.php?email="+myPref.getData(ProfileActivity.this,"email","")+"&first_name="+firstnameET.getText().toString()+"&last_name="+lastnameET.getText().toString()+"&image_path="+imagePath+"&reception="+receptionET.getText().toString()+"&house_keeping="+housekeepET.getText().toString());
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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                profile_img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try
        {
        if(uptype.equals("camera")) {
            uptype = "dfsd";
            if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                profile_img.setImageBitmap(photo);
                 // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), photo);
                imagePath=getRealPathFromURI(tempUri);
                // CALL THIS METHOD TO GET THE ACTUAL PATH
                //File finalFile = new File(imagePath);
                //Toast.makeText(ProfileActivity.this, "Image Path: "+imagePath, Toast.LENGTH_SHORT).show();
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
                imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
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
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
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
                        Toast.makeText(ProfileActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(ProfileActivity.this, "Profile Update Unsuccessfully", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
    class GetProfileDataTask extends AsyncTask<String,Void,String> {
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
                        String user_id=jObj.getString("user_id");
                        emailET.setText(jObj.getString("user_email"));
                        img_path=jObj.getString("image_path");
                        firstnameET.setText(jObj.getString("first_name"));
                        lastnameET.setText(jObj.getString("last_name"));
                        housekeepET.setText(jObj.getString("house_keeping"));
                        receptionET.setText(jObj.getString("reception"));

                        Picasso.with(ProfileActivity.this)
                                .load(img_path)
                                .into(profile_img);
                        //Toast.makeText(ProfileActivity.this, "Get Profile Data", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(ProfileActivity.this, "Profile Update Unsuccessfully", Toast.LENGTH_SHORT).show();
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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(ProfileActivity.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(ProfileActivity.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bitmap);

                //Getting Image Name

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);

                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

}
