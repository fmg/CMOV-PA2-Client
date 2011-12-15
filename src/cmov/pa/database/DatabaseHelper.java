package cmov.pa.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "cmov.db";
	private static final int DATABASE_VERSION = 1;
	
	public final int COLUMN_ID_NUMBER = 0;
	public final String COLUMN_ID_NAME = "_id";
	
	public final int COLUMN_KIND_NUMBER = 1;
	public final String COLUMN_KIND_NAME = "kind";
	
	public final int COLUMN_ADDRESS_NUMBER = 2;
	public final String COLUMN_ADDRESS_NAME = "address";

	public final int COLUMN_CITY_NUMBER = 3;
	public final String COLUMN_CITY_NAME = "city";

	
	public final int COLUMN_BEDROOMS_NUMBER = 4;
	public final String COLUMN_BEDROOMS_NAME = "bedrooms";

	public final int COLUMN_WCS_NUMBER = 5;
	public final String COLUMN_WCS_NAME = "wcs";

	public final int COLUMN_FOR_SALE_NUMBER = 6;
	public final String COLUMN_FOR_SALE_NAME = "for_sale";

	public final int COLUMN_EXTRAS_NUMBER = 7;
	public final String COLUMN_EXTRAS_NAME = "extras";

	public final int COLUMN_PRICE_NUMBER = 8;
	public final String COLUMN_PRICE_NAME = "price";

	public final int COLUMN_PHOTO_NUMBER = 9;
	public final String COLUMN_PHOTO_NAME = "photo";

	
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
