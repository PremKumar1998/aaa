package com.example.login;

import android.content.Context;
import android.content.SharedPreferences;
public class SharedPrefManager {
    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_EMP_CODE = "employee_code";
    private static final String KEY_TOKEN ="token";
    private static final String KEY_FNAME = "f_name";
    private static final String KEY_LNAME = "l_name";
    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(String employee_code,String token, String f_name, String l_name){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMP_CODE, employee_code);
        editor.putString(KEY_TOKEN, token);
        editor.putString(KEY_FNAME, f_name);
        editor.putString(KEY_LNAME, l_name);

        editor.apply();
        return true;
    }

   /* public boolean isLoggegIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_NAME, null) != null){
            return true;
        }
        return false;
    }
*/
    public boolean logout(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

   public String getUsername(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        String fn=sharedPreferences.getString(KEY_FNAME, null);
        String ln=sharedPreferences.getString(KEY_LNAME, null);
        String n=fn+' '+ln;
        return n;

    }

    public String getEmpcode(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMP_CODE, null);
    }

    public String getToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN, null);
    }
}
