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

	public long createFavourite(int id, String kind, String address, String city, String bedrooms, 
			String wcs, String extras , String photo ,boolean for_sale, String price){
		
		int for_saleConvert;
		if(for_sale)
			for_saleConvert = 1;
		else
			for_saleConvert = 0;
		
		
		ContentValues initialvalues = new ContentValues();
		
		initialvalues.put("_id", id);
		initialvalues.put("kind", kind);
		initialvalues.put("address", address);
		initialvalues.put("city", city);
		initialvalues.put("bedrooms", bedrooms);
		initialvalues.put("wcs", wcs);
		initialvalues.put("extras", extras);
		initialvalues.put("photo", photo);
		initialvalues.put("for_sale", for_saleConvert);
		initialvalues.put("price", price);

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
	 		/*
	 		 * HouseInfo(int id, String kind, String address, String city, String bedrooms, 
					String wcs, String extras , String photo ,boolean state, String price)
	 		 */
	 		
	 		boolean for_sale;
	 		if(favouritesCursor.getInt(dbHelper.COLUMN_FOR_SALE) > 0)
	 			for_sale = true;
	 		else
	 			for_sale = false;
	 		
	 		HouseInfo house = new HouseInfo(favouritesCursor.getInt(dbHelper.COLUMN_ID), 
	 				favouritesCursor.getString(dbHelper.COLUMN_KIND),
	 				favouritesCursor.getString(dbHelper.COLUMN_ADDRESS),
	 				favouritesCursor.getString(dbHelper.COLUMN_CITY),
	 				favouritesCursor.getString(dbHelper.COLUMN_BEDROOMS),
	 				favouritesCursor.getString(dbHelper.COLUMN_WCS),
	 				favouritesCursor.getString(dbHelper.COLUMN_EXTRAS),
	 				favouritesCursor.getString(dbHelper.COLUMN_PHOTO),
	 				for_sale,
	 				favouritesCursor.getString(dbHelper.COLUMN_PRICE));
	 		
	 		favourites.add(house);
	 	}while(favouritesCursor.moveToNext());
	 	favouritesCursor.close();
	 	
	 	return favourites;
	}
	
	
	
	

}
