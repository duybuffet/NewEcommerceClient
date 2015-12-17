package com.ptit.bookecommerce.utils;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "MyDBName.db";
	public static final String FAVORITE_TABLE_NAME = "favorites";
	public static final String FAVORITE_COLUMN_ID = "id";
	public static final String FAVORITE_COLUMN_EBOOK_ID = "ebook_id";
	private HashMap hp;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table favorites "
				+ "(id integer primary key, ebook_id integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS favorites");
		onCreate(db);
	}

	public boolean insert(int ebookId) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("ebook_id", ebookId);
		db.insert("favorites", null, contentValues);
		Log.d("INSERT DB", "success");
		Log.d("INSERT id", ebookId + "");
		return true;
	}

	public Cursor getData(int id) {
		Log.d("Get Data", "success");
		Log.d("Get data ebook id : ", id + "");
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from favorites where ebook_id=" + id
				+ "", null);
		Log.d("Cursor res", res.moveToFirst() + "");
		return res;
	}

	public ArrayList<Integer> getAllFavorites() {
		ArrayList<Integer> array_list = new ArrayList<Integer>();

		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from favorites", null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {
			array_list.add(res.getInt(res
					.getColumnIndex(FAVORITE_COLUMN_EBOOK_ID)));
			res.moveToNext();
		}
		return array_list;
	}
};
