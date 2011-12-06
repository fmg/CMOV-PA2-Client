package cmov.pa.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {
	
	private Context context;
	private static SQLiteDatabase database;
	private static DatabaseHelper dbHelper;
	
	
	public DatabaseAdapter(Context context) {
		this.context = context;
	}

	public DatabaseAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	
	public void resetAll() { 
		String resetFavourites = "delete from favourites";  

	 	database.execSQL(resetFavourites);
	 	
	}
		
		
/////////////////////////////////////////////////////////////////////

	public long createFavourite(int id, String name, String address, int bedrooms) {
		
		ContentValues initialvalues = new ContentValues();
		initialvalues.put("_id", id);
		initialvalues.put("name", name);
		initialvalues.put("address", address);
		initialvalues.put("bedrooms", bedrooms);
		initialvalues.put("photo", "");

		return database.insert("favourites", null, initialvalues);
	}
	
	
	
	

}
