package com.jiangzhou.tdp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TDPDbHelper extends SQLiteOpenHelper {

	public TDPDbHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public TDPDbHelper(Context context, String name, CursorFactory factory,
            int version) {
        super(context, name, factory, version);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("qiqi", "start create tables");
		db.execSQL("CREATE TABLE " + Category.TABLE_NAME + "("
				+ Category.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Category.COLUMN_DEFAULT_NAME + " TEXT,"
				+ Category.COLUMN_DEFAULT_PORT + " TEXT,"
				+ Category.COLUMN_DEFAULT_LAND + " TEXT"
				+ ")");
		
		db.execSQL("CREATE TABLE " + Tag.TABLE_NAME + "("
				+ Tag.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Tag.COLUMN_DEFAULT_NAME + " TEXT"
				+ ")");
		
		db.execSQL("CREATE TABLE " + Pic.TABLE_NAME + "("
				+ Pic.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Pic.COLUMN_DEFAULT_NAME + " TEXT,"
				+ Pic.COLUMN_DEFAULT_MODE + " TEXT,"
				+ Pic.COLUMN_DEFAULT_CATEGORY + " TEXT,"
				+ Pic.COLUMN_DEFAULT_TAG + " TEXT"
				+ ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}