package cmov.pa.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "cmov.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE_FAVOURITES = " CREATE TABLE favourites (_id INTEGER PRIMARY KEY, type TEXT,  address TEXT, bedrooms INT, photo BLOB)";
	


	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_FAVOURITES);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(DatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		
		database.execSQL(" DROP TABLE IF EXISTS favourites");
		
		onCreate(database);
	}

}
