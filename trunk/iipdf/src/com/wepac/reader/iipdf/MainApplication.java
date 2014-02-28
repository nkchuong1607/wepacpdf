package com.wepac.reader.iipdf;

import com.wepac.reader.iipdf.db.OpenedFileDbHelper;
import com.wepac.reader.iipdf.preference.AppPreference;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {

	private static Context context;
	
	public static Context getAppContext(){
		return context;
	}
	
	@Override
	public void onCreate() {
		context = getApplicationContext();
		AppPreference.initialize(context);
		OpenedFileDbHelper.getInstance();
		super.onCreate();
	}
}
