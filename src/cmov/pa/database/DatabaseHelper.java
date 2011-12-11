package cmov.pa.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "cmov.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final int COLUMN_ID = 0;
	public static final int COLUMN_KIND = 1;
	public static final int COLUMN_ADDRESS = 2;
	public static final int COLUMN_CITY = 3;
	public static final int COLUMN_BEDROOMS = 4;
	public static final int COLUMN_WCS = 5;
	public static final int COLUMN_FOR_SALE = 6;
	public static final int COLUMN_EXTRAS = 7;
	public static final int COLUMN_PRICE = 8;
	public static final int COLUMN_PHOTO = 9;
	
	private static final String DATABASE_CREATE_FAVOURITES = " CREATE TABLE favourites (_id INTEGER PRIMARY KEY, " +
			"kind TEXT,  " +
			"address TEXT, " +
			"city TEXT, " +
			"bedrooms TEXT, " +
			"wcs TEXT ," +
			"for_sale INT, " +
			"extras TEXT, " +
			"price TEXT, " +
			"photo TEXT)";
	
	private static final String DATABASE_CREATE_VERSION = " CREATE TABLE version (date TEXT PRIMARY KEY)";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE_FAVOURITES);
		database.execSQL(DATABASE_CREATE_VERSION);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(DatabaseHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		
		database.execSQL(" DROP TABLE IF EXISTS favourites");
		database.execSQL(" DROP TABLE IF EXISTS version");
		
		onCreate(database);
	}

}
