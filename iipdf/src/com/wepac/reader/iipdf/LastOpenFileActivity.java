package com.wepac.reader.iipdf;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.wepac.reader.iipdf.db.OpenedFileDbHelper;

public class LastOpenFileActivity extends Activity {

	private ListView lvFile;
	private LastOpenFileAdapter mFileAdapter;
	private ArrayList<File> mFileList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.last_open_file);
		setTitle(R.string.last_open);
		
		mFileList = OpenedFileDbHelper.getInstance().getAllFile();
		mFileAdapter = new LastOpenFileAdapter(this, mFileList);
		
		ImageButton btnBrowseFile = (ImageButton) findViewById(R.id.browse_file);
		btnBrowseFile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LastOpenFileActivity.this, ChoosePDFActivity.class);
				startActivity(intent);
			}
		});
		
		lvFile = (ListView) findViewById(R.id.list_file);
		lvFile.setAdapter(mFileAdapter);
		lvFile.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Uri uri = Uri.parse(mFileList.get(position).getAbsolutePath());
				Intent intent = new Intent(LastOpenFileActivity.this,MuPDFActivity.class);
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(uri);
				startActivity(intent);
			}
		});
		
		registerForContextMenu(lvFile);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		refreshFileList();
	}

	private void refreshFileList() {
		mFileList = OpenedFileDbHelper.getInstance().getAllFile();
		mFileAdapter.setData(mFileList);
	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if(v.getId() == R.id.list_file){
			getMenuInflater().inflate(R.menu.opened_file_menu, menu);
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.delete:
			int position = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
			OpenedFileDbHelper.getInstance().deleteOpenFile(mFileList.get(position).getAbsolutePath());
			refreshFileList();
			return true;
		}
		return super.onContextItemSelected(item);
	}
}
