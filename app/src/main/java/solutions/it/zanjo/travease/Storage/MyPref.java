package solutions.it.zanjo.travease.Storage;

import android.content.Context;
import android.content.SharedPreferences;

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

    public void saveData(Context context, String key, int value)
    {
        sp = context.getSharedPreferences("ID",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getData(Context context,String key,int value)
    {
        sp = context.getSharedPreferences("ID",context.MODE_PRIVATE);
        return sp.getInt(key, value);
    }

    public void ClearID(Context context)
    {
        sp = context.getSharedPreferences("ID",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
}
