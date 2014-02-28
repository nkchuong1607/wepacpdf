package com.wepac.reader.iipdf.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {

	private static SharedPreferences mPreference;
	public static void initialize(Context context){
		mPreference = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	private static String LAST_DIR = "last_dir";
	public static void setLastDir(String path){
		SharedPreferences.Editor editor = mPreference.edit();
		editor.putString(LAST_DIR, path);
		editor.commit();
	}
	
	public static String getLastDir(){
		return mPreference.getString(LAST_DIR, "");
	}
}
