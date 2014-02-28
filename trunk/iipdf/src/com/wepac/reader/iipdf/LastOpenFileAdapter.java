package com.wepac.reader.iipdf;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LastOpenFileAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<File> mFileList;
	public LastOpenFileAdapter(Context context, ArrayList<File> fileList){
		mContext = context;
		mFileList = fileList;
	}
	
	public void setData(ArrayList<File> list){
		mFileList = list;
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		if(mFileList != null){
			return mFileList.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if(mFileList != null){
			return mFileList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.open_file_row, null);
			
			holder.fileName = (TextView) convertView.findViewById(R.id.file_name);
			
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.fileName.setText(mFileList.get(position).getName());
		
		return convertView;
	}
	
	class ViewHolder {
		TextView fileName;
	}

}
