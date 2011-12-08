package cmov.pa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cmov.pa.utils.HouseInfo;

import android.app.Application;


public class Api extends Application{
	
	public static String IP = "http://95.92.19.188:3001";
	public static cmov.pa.database.DatabaseAdapter dbAdapter;
	public ArrayList<HouseInfo> list;
	
	public Api(){
		list = new ArrayList<HouseInfo>();
	}
	
	public int RegisterKey(String key){
		
		final HttpClient httpClient =  new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 3000);
		HttpResponse response=null;
	   	
		String url = IP + " /registration/create?name="+key;       
		System.out.println(url);
		try {
			HttpGet httpget = new HttpGet(url);		   
			httpget.setHeader("Accept", "application/json");
	   
			response = httpClient.execute(httpget);
   
			if(response.getStatusLine().getStatusCode() == 200){
				return 0;
			}else{
				return -1;
			}
		   
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return 1;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
           
       
	}
	
	
	
	
	public ArrayList<HouseInfo> updateList(String date) throws ClientProtocolException, IOException, JSONException{
		
		final HttpClient httpClient =  new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 3000);
		HttpResponse response=null;
		String url;
		
		ArrayList<HouseInfo> houses = new ArrayList<HouseInfo>();
		
		if(date == null)
			url = IP + " /properties/update";
		else
			url = IP + " /properties/update?date="+date;
		
		System.out.println(url);
		
		
		HttpGet httpget = new HttpGet(url);		   
		httpget.setHeader("Accept", "application/json");
	   
		response = httpClient.execute(httpget);
   
		if(response.getStatusLine().getStatusCode() == 200){
			InputStream instream = response.getEntity().getContent();
            String tmp = read(instream);
            
        	
	        JSONArray messageReceived = new JSONArray(tmp.toString());
        	System.out.println(messageReceived.toString());
        	
        	for(int i = 0; i < messageReceived.length(); i++){
        		JSONObject jo = messageReceived.getJSONObject(i);
        		
        		/*
        		 * public HouseInfo(int id, String kind, String address, String city, String bedrooms, 
					String wcs, String extras , String photo ,boolean state, String price)
        		 */
        		
        		String photo = "/system/photos/"+jo.getInt("id")+"/original/"+jo.getString("photo");
   		
        		HouseInfo h = new HouseInfo(jo.getInt("id"), 
        				jo.getString("kind"), 
        				jo.getString("address"), 
        				jo.getString("city"), 
        				jo.getString("rooms"), 
        				jo.getString("wcs"),
        				jo.getString("extras"),
        				photo,
        				jo.getBoolean("for_sale"),
        				jo.getString("price")); 
        		
        		houses.add(h);
        		
        	}
        	
        	return houses;
		}

		return houses;

	}
	
	
	public void inserFavourite(HouseInfo house){
		dbAdapter.open();
		
		dbAdapter.createFavourite(house.getId(), house.getKind(), house.getAddress(), house.getCity(), 
									house.getBedrooms(), house.getWcs(), house.getExtras(), 
									house.getPhoto(), house.isFor_sale(), house.getPrice());
		
		dbAdapter.close();
	}
	
	
	
	private String read(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        in.close();
        return sb.toString();
}

}
