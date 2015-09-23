package com.jiangzhou.tdp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class TDPProvider extends ContentProvider {
	public static final String DB_NAME = "tdp.db";

    public static final String URI_AUTHORITY = "com.jiangzhou.tdp";
    
    private static final int URI_CODE_PAGE= 1;
    private static final int URI_CODE_PAGE_ID = 2;
    private static final int URI_CODE_PIC = 3;
    private static final int URI_CODE_PIC_ID = 4;
    
    public static final String URI_MIME_TEXT
        = "vnd.android.cursor.dir/vnd.tdp.page";
    public static final String URI_ITEM_MIME_TEXT
        = "vnd.android.cursor.item/vnd.tdp.page";
    public static final String URI_MIME_IMAGE
    	= "vnd.android.cursor.dir/vnd.tdp.pic";
    public static final String URI_ITEM_MIME_IMAGE
    	= "vnd.android.cursor.item/vnd.tdp.pic";

    private static final String TAG = TDPProvider.class.getSimpleName();

    private static UriMatcher mUriMatcher;
    private TDPDbHelper mMemoDbHelper;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        
        mUriMatcher.addURI(URI_AUTHORITY, Category.TABLE_NAME, URI_CODE_PAGE);
        mUriMatcher.addURI(URI_AUTHORITY, Category.TABLE_NAME + "/#", URI_CODE_PAGE_ID);
        mUriMatcher.addURI(URI_AUTHORITY, Pic.TABLE_NAME, URI_CODE_PIC);
        mUriMatcher.addURI(URI_AUTHORITY, Pic.TABLE_NAME + "/#", URI_CODE_PIC_ID);
    }

	@Override
	public boolean onCreate() {
		Log.d("qiqi", "TDPProvider onCreate");
		mMemoDbHelper = new TDPDbHelper(getContext(), DB_NAME, 1);
		return true;
	}
	
	@Override
	public String getType(Uri uri) {
		Log.e("memo", "=======================================uri:" + uri);
		switch (mUriMatcher.match(uri)) {
        case URI_CODE_PAGE:
            return URI_MIME_TEXT;
        case URI_CODE_PAGE_ID:
            return URI_ITEM_MIME_TEXT;
        case URI_CODE_PIC:
            return URI_MIME_IMAGE;
        case URI_CODE_PIC_ID:
            return URI_ITEM_MIME_IMAGE;
        default:
            Log.e(TAG, "Unknown URI:" + uri);
            throw new IllegalArgumentException("Unknown URI:" + uri);
		}
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowId;
		Uri rowUri = null;
		SQLiteDatabase db = mMemoDbHelper.getWritableDatabase();
		switch (mUriMatcher.match(uri)) {
		case URI_CODE_PAGE:
			rowId = db.insert(Category.TABLE_NAME, null, values);
			if (rowId != -1) {
				rowUri = ContentUris.withAppendedId(Category.CONTENT_URI, rowId);
			}
			break;
		case URI_CODE_PIC:
			rowId = db.insert(Pic.TABLE_NAME, null, values);
			if (rowId != -1) {
				rowUri = ContentUris.withAppendedId(Pic.CONTENT_URI, rowId);
			}
			break;
		}
		db.close();
		return rowUri;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		SQLiteDatabase db = mMemoDbHelper.getWritableDatabase();
		switch (mUriMatcher.match(uri)) {
		case URI_CODE_PAGE:
			count = db.delete(Category.TABLE_NAME, selection, selectionArgs);
			break;
		case URI_CODE_PAGE_ID:
			count = db.delete(Category.TABLE_NAME,
					Category.COLUMN_ID
							+ "="
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ")" : ""), selectionArgs);
			break;
			
		case URI_CODE_PIC:
			count = db.delete(Pic.TABLE_NAME, selection, selectionArgs);
			break;
		case URI_CODE_PIC_ID:
			count = db.delete(Pic.TABLE_NAME,
					Pic.COLUMN_ID
							+ "="
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ")" : ""), selectionArgs);
			break;
		default:
			Log.e(TAG, "Unknown URI:" + uri);
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		db.close();
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = mMemoDbHelper.getReadableDatabase();
		int count;
		switch (mUriMatcher.match(uri)) {
		case URI_CODE_PAGE:
			count = db.update(Category.TABLE_NAME, values, selection, selectionArgs);
			break;
		case URI_CODE_PAGE_ID:
			count = db.update(Category.TABLE_NAME, values,
					Category.COLUMN_ID
							+ "="
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ")" : ""), selectionArgs);
			break;
			
		case URI_CODE_PIC:
			count = db.update(Pic.TABLE_NAME, values, selection, selectionArgs);
			break;
		case URI_CODE_PIC_ID:
			count = db.update(Pic.TABLE_NAME, values,
					Pic.COLUMN_ID
							+ "="
							+ uri.getLastPathSegment()
							+ (!TextUtils.isEmpty(selection) ? " AND ("
									+ selection + ")" : ""), selectionArgs);
			break;
			
		default:
			Log.e(TAG, "Unknown URI:" + uri);
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		db.close();
		return count;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		Log.e("memo", "=======================================uri:" + uri);
		Log.e("memo", "=======================================mUriMatcher.match(uri):" + mUriMatcher.match(uri));
		switch (mUriMatcher.match(uri)) {
		case URI_CODE_PAGE:
			sqlBuilder.setTables(Category.TABLE_NAME);
			break;
		case URI_CODE_PAGE_ID:
			sqlBuilder.setTables(Category.TABLE_NAME);
			sqlBuilder.appendWhere(Category.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
			
		case URI_CODE_PIC:
			sqlBuilder.setTables(Pic.TABLE_NAME);
			break;
		case URI_CODE_PIC_ID:
			sqlBuilder.setTables(Pic.TABLE_NAME);
			sqlBuilder.appendWhere(Pic.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
			
		default:
			Log.e(TAG, "Unknown URI:" + uri);
			throw new IllegalArgumentException("Unknown URI:" + uri);
		}
		cursor = sqlBuilder.query(mMemoDbHelper.getReadableDatabase(), projection,
				selection, selectionArgs, null, null, sortOrder);
		return cursor;
	}
	
}