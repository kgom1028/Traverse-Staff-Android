package solutions.it.zanjo.travease.Commons;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import solutions.it.zanjo.travease.Activities.LoginActivity;
import solutions.it.zanjo.travease.Activities.NoInternetActivity;


public class Common {

    public static final String SERVER_URL = "http://zanjo.io/projects/Traverse_api/";


    private static Context context;

//save the context recievied via constructor in a local variable

    public Common(Context context){
        this.context=context;
    }


    public static final boolean isInternetOn() {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) MyApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {

            // if connected with internet

            //   Toast.makeText(getActivity(), " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {

           // Toast.makeText(MyApplication.getAppContext(), " Not Connected ", Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }



}
