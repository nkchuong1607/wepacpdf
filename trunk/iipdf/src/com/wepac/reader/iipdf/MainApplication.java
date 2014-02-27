package com.wepac.reader.iipdf;

import com.wepac.reader.iipdf.db.OpenedFileDbHelper;

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
		OpenedFileDbHelper.getInstance();
		super.onCreate();
	}
}
