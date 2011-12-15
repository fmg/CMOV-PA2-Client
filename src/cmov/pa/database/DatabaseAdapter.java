package cmov.pa.database;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
	 	
	 	
	 	String resetVersion = "delete from version";  

	 	database.execSQL(resetVersion);
	 	
	}
		
		
/////////////////////////////////////////////////////////////////////
	
	
	public boolean hasFavourite(int id){
		String selectFavourite = "Select * from favourites where _id="+id;  

		Cursor favouritesCursor = database.rawQuery(selectFavourite, null);
		
		
		if(favouritesCursor.getCount() > 0)
			return true;
		else
			return false;
		
	}
	
	public void removeFavourite(int id){
		String deleteFavourite = "delete from favourites where _id="+id;  

	 	database.execSQL(deleteFavourite);
	}
	

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
	 		if(favouritesCursor.getInt(dbHelper.COLUMN_FOR_SALE_NUMBER) > 0)
	 			for_sale = true;
	 		else
	 			for_sale = false;
	 		
	 		HouseInfo house = new HouseInfo(favouritesCursor.getInt(dbHelper.COLUMN_ID_NUMBER), 
	 				favouritesCursor.getString(dbHelper.COLUMN_KIND_NUMBER),
	 				favouritesCursor.getString(dbHelper.COLUMN_ADDRESS_NUMBER),
	 				favouritesCursor.getString(dbHelper.COLUMN_CITY_NUMBER),
	 				favouritesCursor.getString(dbHelper.COLUMN_BEDROOMS_NUMBER),
	 				favouritesCursor.getString(dbHelper.COLUMN_WCS_NUMBER),
	 				favouritesCursor.getString(dbHelper.COLUMN_EXTRAS_NUMBER),
	 				favouritesCursor.getString(dbHelper.COLUMN_PHOTO_NUMBER),
	 				for_sale,
	 				favouritesCursor.getString(dbHelper.COLUMN_PRICE_NUMBER));
	 		
	 		favourites.add(house);
	 	}while(favouritesCursor.moveToNext());
	 	favouritesCursor.close();
	 	
	 	return favourites;
	}
	
	
	
	public HouseInfo getFavourite(int id){
		
		
		String selectFavourite = "Select * from favourites Where _id="+id;  
		
	 	Cursor favouriteCursor = database.rawQuery(selectFavourite, null);
	 	
	 	favouriteCursor.moveToFirst();
		
		boolean for_sale;
 		if(favouriteCursor.getInt(dbHelper.COLUMN_FOR_SALE_NUMBER) > 0)
 			for_sale = true;
 		else
 			for_sale = false;
 		
 		HouseInfo house = new HouseInfo(favouriteCursor.getInt(dbHelper.COLUMN_ID_NUMBER), 
 				favouriteCursor.getString(dbHelper.COLUMN_KIND_NUMBER),
 				favouriteCursor.getString(dbHelper.COLUMN_ADDRESS_NUMBER),
 				favouriteCursor.getString(dbHelper.COLUMN_CITY_NUMBER),
 				favouriteCursor.getString(dbHelper.COLUMN_BEDROOMS_NUMBER),
 				favouriteCursor.getString(dbHelper.COLUMN_WCS_NUMBER),
 				favouriteCursor.getString(dbHelper.COLUMN_EXTRAS_NUMBER),
 				favouriteCursor.getString(dbHelper.COLUMN_PHOTO_NUMBER),
 				for_sale,
 				favouriteCursor.getString(dbHelper.COLUMN_PRICE_NUMBER));
		
		favouriteCursor.close();
 		
		return house;
	}
	
	
	
	public long updateFavourite(HouseInfo house){
		
		
		String strFilter = "_id=" + house.getId();
		ContentValues args = new ContentValues();
		args.put(dbHelper.COLUMN_KIND_NAME, house.getKind());
		args.put(dbHelper.COLUMN_ADDRESS_NAME, house.getAddress());
		args.put(dbHelper.COLUMN_CITY_NAME, house.getKind());
		args.put(dbHelper.COLUMN_BEDROOMS_NAME, house.getKind());
		args.put(dbHelper.COLUMN_WCS_NAME, house.getKind());
		args.put(dbHelper.COLUMN_EXTRAS_NAME, house.getKind());
		args.put(dbHelper.COLUMN_PHOTO_NAME, house.getKind());
		args.put(dbHelper.COLUMN_PRICE_NAME, house.getKind());

		if(house.isFor_sale())
			args.put(dbHelper.COLUMN_FOR_SALE_NAME, 1);
		else
			args.put(dbHelper.COLUMN_FOR_SALE_NAME, 0);

		return database.update("favourites", args, strFilter, null);
 		
		
	}
	
	
/////////////////////////////////////////////////////////////////////
	
	public String getLastUpdateDate(){
		
		
		String selectversion = "Select * from version";  
		
	 	Cursor versionCursor = database.rawQuery(selectversion, null);
	 	
	 	versionCursor.moveToFirst();
	 	
	 	if(versionCursor.getCount() == 0){
	 		versionCursor.close();
	 		return "";
	 	}
	 	
	 	String ret =versionCursor.getString(0);
	 	versionCursor.close();
	 	
	 	return ret;

		
	}
	
	
	public long updateVersion(){

		//elimina data antiga
		String resetVersion = "delete from version";  
	 	database.execSQL(resetVersion);
		
	 	
	 	
	 	//insere nova
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		
	
		ContentValues initialvalues = new ContentValues();
		initialvalues.put("date", date);
		
		return database.insert("version", null, initialvalues);
	
	}
	

}
