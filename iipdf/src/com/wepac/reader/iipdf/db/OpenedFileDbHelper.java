package com.wepac.reader.iipdf.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wepac.reader.iipdf.MainApplication;

public class OpenedFileDbHelper extends SQLiteOpenHelper{
	public static String TABLE_NAME = "openedfiles";
	public static String COLUMN_FILE_NAME = "filename";
	public static String COLUMN_ID = "id";
	
	private static final String SQL_CREATE_OPEN_FILE_TABLE = "create table " + TABLE_NAME
			+ "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_FILE_NAME + " TEXT)";

	private static OpenedFileDbHelper instance;
	private SQLiteDatabase db;
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "OpenedFile.db";

	public OpenedFileDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = context.openOrCreateDatabase(DATABASE_NAME, 0, null);
	}
	
	public synchronized static OpenedFileDbHelper getInstance(){
		if(instance == null){
			synchronized (OpenedFileDbHelper.class) {
				instance = new OpenedFileDbHelper(MainApplication.getAppContext());
			}
			instance.createOpenFileTable();
		}
		return instance;
	}
	
	
	public void createOpenFileTable(){
		if(!isExistTable(TABLE_NAME)){
			db.execSQL(SQL_CREATE_OPEN_FILE_TABLE);
		}
	}
	
	public void insertOpenFile(String filePath){
		if(filePath != null && filePath.length() > 0){
			db.execSQL("insert into "+ TABLE_NAME
									+ " ("+ COLUMN_FILE_NAME + ")"
									+ "values (?)", new String[] {filePath});
		}
	}
	
	public void deleteOpenFile(String filePath){
		try{
			if(filePath != null && filePath.length() > 0){
				db.beginTransaction();
				db.delete(TABLE_NAME, COLUMN_FILE_NAME + " = ?", new String[] {filePath});
				db.setTransactionSuccessful();
				db.endTransaction();
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public boolean isFileInDB(String filePath){
		try{
			Cursor cursor = null;
			cursor = db.rawQuery("SELECT EXISTS(SELECT 1 FROM "+ TABLE_NAME +" WHERE "+ COLUMN_FILE_NAME +"= ? LIMIT 1)", new String[] {filePath});
			if(cursor != null){
				int result = cursor.getInt(0);
				cursor.close();
				cursor = null;
				if(result != 1){
					return false;
				}
			}
			return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return true;
		}
	}
	
	public boolean isExistTable(String tableName) {
		boolean isExist = true;

		Cursor cursor = null;
		try {
			cursor = db.rawQuery("SELECT * FROM SQLITE_MASTER WHERE NAME = '" + tableName + "'", null);
			if (cursor != null && cursor.getCount() == 0) {
				isExist = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
			{
				cursor.close();
				cursor = null;
			}
		}

		return isExist;
	}
	
	@Override
	public synchronized void close() {
		synchronized (db) {
			if (db != null)
				db.close();
		}
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}


}
