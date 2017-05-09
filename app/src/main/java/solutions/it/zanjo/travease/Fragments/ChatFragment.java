package solutions.it.zanjo.travease.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import solutions.it.zanjo.travease.Activities.ChatMessageActivity;
import solutions.it.zanjo.travease.Activities.NoInternetActivity;
import solutions.it.zanjo.travease.Adapter.ChatAdapter;
import solutions.it.zanjo.travease.Adapter.HomeListAdapter;
import solutions.it.zanjo.travease.Commons.Common;
import solutions.it.zanjo.travease.Model.ChatList;
import solutions.it.zanjo.travease.Model.Home_Work;
import solutions.it.zanjo.travease.R;

/**
 * Created by abc on 4/24/2017.
 */

public class ChatFragment extends Fragment {

    ListView lv1;
    ChatAdapter chatAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        TextView title= (TextView) toolbar.findViewById(R.id.TitleTV);
        title.setText("Chat");

        lv1 = (ListView) view.findViewById(R.id.listview);
        chatAdapter=new ChatAdapter(getActivity());
        lv1.setAdapter(chatAdapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), ChatMessageActivity.class));
            }
        });
        if (Common.isInternetOn())
        {
            new ChatListDataTask().execute(Common.SERVER_URL+"Get_All_ChatRooms.php");
        }else  startActivity(new Intent(getActivity(),NoInternetActivity.class));

        return view;
    }
    class ChatListDataTask extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
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
                        JSONArray jsonArray = jObj.getJSONArray("Get All ChatRooms Successfully");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonUser = jsonArray.getJSONObject(i);
                            String Id=jsonUser.getString("Id");
                            String Name=jsonUser.getString("Name");
                            String Time=jsonUser.getString("Time");
                            String room_no=jsonUser.getString("room_no");

                            ChatList chatList=new ChatList(Id,Name,Time,room_no);
                            chatAdapter.addItem(chatList);
                        }
                        lv1.setAdapter(chatAdapter);
                        chatAdapter.notifyDataSetChanged();
                        //Toast.makeText(getActivity(), "Show Data", Toast.LENGTH_SHORT).show();
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
}
