package com.jiangzhou.tdp;

import android.content.ContentValues;
import android.net.Uri;

public class Tag {

	public final static String TABLE_NAME = "tag";

	public final static String COLUMN_ID = "_id";
	public final static String COLUMN_DEFAULT_NAME = "default_name";
	
	public final static Uri CONTENT_URI = Uri.parse("content://"
			+ TDPProvider.URI_AUTHORITY + "/" + TABLE_NAME);

	public long mId;
	public int mDefaultName;
	
	public ContentValues toContentValues() {
		ContentValues values = new ContentValues();
		if(mId != 0) {
			values.put(COLUMN_ID, mId);
		}
		values.put(COLUMN_DEFAULT_NAME, mDefaultName);
		return values;
	}
}