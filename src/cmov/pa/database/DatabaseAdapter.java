package cmov.pa.database;

import java.util.ArrayList;

import cmov.pa.utils.HouseInfo;

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

	public long createFavourite(int id, String name, String address, String city, int bedrooms) {
		
		ContentValues initialvalues = new ContentValues();
		initialvalues.put("_id", id);
		initialvalues.put("name", name);
		initialvalues.put("address", address);
		initialvalues.put("city", city);
		initialvalues.put("bedrooms", bedrooms);

		return database.insert("favourites", null, initialvalues);
	}
	
	
	public ArrayList<HouseInfo> getFavourites(){
		
		ArrayList<HouseInfo> favourites = new ArrayList<HouseInfo>();
		
		String selectFavourites = "Select * from favourites Order By _id";  
		
	 	Cursor favouritesCursor = database.rawQuery(selectFavourites, null);
	 	
	 	favouritesCursor.moveToFirst();
	 	if(favouritesCursor.getCount() == 0){
	 		favouritesCursor.close();
	 		return favourites;
	 	}
	 	
	 	favouritesCursor.moveToFirst();
	 	do {
	 		HouseInfo house = new HouseInfo(favouritesCursor.getInt(0), favouritesCursor.getString(1),favouritesCursor.getString(2),favouritesCursor.getString(3),favouritesCursor.getInt(4),favouritesCursor.getString(5));
	 		favourites.add(house);
	 	}while(favouritesCursor.moveToNext());
	 	favouritesCursor.close();
	 	
	 	return favourites;
	}
	
	
	
	

}
