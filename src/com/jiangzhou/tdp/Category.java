package com.jiangzhou.tdp;

import android.content.ContentValues;
import android.net.Uri;

public class Category {

	public final static String TABLE_NAME = "category";

	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_DEFAULT_NAME = "default_name";
	public final static String COLUMN_DEFAULT_PORT = "default_port";
	public final static String COLUMN_DEFAULT_LAND = "default_land";
	
	public final static Uri CONTENT_URI = Uri.parse("content://"
			+ TDPProvider.URI_AUTHORITY + "/" + TABLE_NAME);

	public long mId;
	public int mDefaultName;
	public int mDefaultPort;
	public String mDefaultLand;
	
	public ContentValues toContentValues() {
		ContentValues values = new ContentValues();
		if(mId != 0) {
			values.put(COLUMN_ID, mId);
		}
		values.put(COLUMN_DEFAULT_NAME, mDefaultName);
		values.put(COLUMN_DEFAULT_PORT, mDefaultPort);
		values.put(COLUMN_DEFAULT_LAND, mDefaultLand);
		return values;
	}
}