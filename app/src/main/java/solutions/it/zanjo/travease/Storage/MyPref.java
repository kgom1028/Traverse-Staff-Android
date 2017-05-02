package solutions.it.zanjo.travease.Storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by abc on 5/1/2017.
 */

public class MyPref {

    SharedPreferences sp;

    public void saveData(Context context, String key, String value)
    {
        sp = context.getSharedPreferences("mypref",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getData(Context context,String key,String value)
    {
        sp = context.getSharedPreferences("mypref",context.MODE_PRIVATE);
        return sp.getString(key, value);
    }

    public void Clear(Context context)
    {
        sp = context.getSharedPreferences("mypref",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
