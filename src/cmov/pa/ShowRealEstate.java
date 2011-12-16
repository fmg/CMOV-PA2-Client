package cmov.pa;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import cmov.pa.utils.HouseInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class ShowRealEstate  extends Activity implements Runnable{
	
	
	Api api;
	ProgressDialog dialog;
	
	 private static final int SWIPE_MIN_DISTANCE = 150;
	 private static final int SWIPE_MAX_OFF_PATH = 200;
	 private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	 private GestureDetector gestureDetector;
	 View.OnTouchListener gestureListener;
	 
	 
	 ArrayList<Integer> ids_list;
	 int index ;
	 String mode;
	 
	 HouseInfo hinfo;
	
  	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_real_estate);
        
        api = (Api)getApplicationContext();
        
        Bundle extras = getIntent().getExtras();
        ids_list = extras.getIntegerArrayList("ids_list");
		index = extras.getInt("index");
		mode = extras.getString("mode");
		
		System.out.println(index + " -> " + ids_list);
        
        gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };
        
        dialog = ProgressDialog.show(ShowRealEstate.this, "", "Loading. Please wait...", true);
		Thread thread = new Thread(this);
        thread.start();
    }
  	
  	@Override
	public void run() {
  		try {
  		
	  		if(mode.equalsIgnoreCase(api.MODE_NOTIFICATION_NEW) || mode.equalsIgnoreCase(api.MODE_NOTIFICATION_UPDATE)){
	  			
	  			hinfo =	api.getHouseInfo(ids_list.get(index));
	  			
	  			
	  			if(mode.equalsIgnoreCase(api.MODE_NOTIFICATION_UPDATE)){
	  				api.updateFavourite(hinfo);
	  			}
	  			
				
				
	  		}else if(mode.equalsIgnoreCase(api.MODE_FAVOURITE) || mode.equalsIgnoreCase(api.MODE_AVAILABLE_UPDATE)){
	  			hinfo = api.getFavourite(ids_list.get(index));
	  		}else if(mode.equalsIgnoreCase(api.MODE_AVAILABLE_NEW)){
	  			hinfo = api.available_new_list.get(index);
	  		}
	  			
	  		handler.sendMessage(handler.obtainMessage());
  		
  		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
  	
  	
  	final Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        	((TextView)findViewById(R.id.view_house_type)).setText(hinfo.getKind());
        	((TextView)findViewById(R.id.view_house_bedrooms)).setText(hinfo.getBedrooms());
        	
        	((TextView)findViewById(R.id.view_house_price)).setText(hinfo.getPrice());
        	
        	if(hinfo.isFor_sale()){
        		((TextView)findViewById(R.id.view_house_selling_state)).setText("For Sale");
        		((ScrollView)findViewById(R.id.ScrollView1)).setBackgroundResource(R.drawable.background_selling);
        	}else{
        		((TextView)findViewById(R.id.view_house_selling_state)).setText("Sold");
        		((ScrollView)findViewById(R.id.ScrollView1)).setBackgroundResource(R.drawable.background_sold);
        	}
        	
        	((TextView)findViewById(R.id.view_house_address)).setText(hinfo.getAddress());
        	((TextView)findViewById(R.id.view_house_wc)).setText(hinfo.getWcs());
        	((TextView)findViewById(R.id.view_house_extras)).setText(hinfo.getExtras());
        	((TextView)findViewById(R.id.view_house_city)).setText(hinfo.getCity());
        	
        	
        	if(mode.equalsIgnoreCase(api.MODE_FAVOURITE)  || mode.equalsIgnoreCase(api.MODE_NOTIFICATION_UPDATE) || mode.equalsIgnoreCase(api.MODE_AVAILABLE_UPDATE)){
        		((RatingBar)findViewById(R.id.view_house_favourite)).setRating(1);
        	}
        	
        	
        	URL newurl;
			try {
				newurl = new URL(hinfo.getPhoto());
			
				Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());	
				((ImageView)findViewById(R.id.view_house_image)).setImageBitmap(mIcon_val);
			
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
		 ((RatingBar)findViewById(R.id.view_house_favourite)).setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
				
				@Override
				public void onRatingChanged(RatingBar ratingBar, float rating,
						boolean fromUser) {
					
					if(rating == 0){
						System.out.println("vai remover dos favoritos");
						api.deleteFavourite(hinfo.getId());
						Toast toast = Toast.makeText(getApplicationContext(), "Favourite removed", Toast.LENGTH_SHORT);
		        		toast.show();
					}else{
						System.out.println("vai adicionar aos favoritos");
						api.inserFavourite(hinfo);
						Toast toast = Toast.makeText(getApplicationContext(), "Favourite added", Toast.LENGTH_SHORT);
		        		toast.show();
					}
					
				}
			});
			
			
        	dialog.dismiss();
        }
    };

  	
  	@Override
  	public boolean dispatchTouchEvent(MotionEvent ev){
  		super.dispatchTouchEvent(ev);
  		return gestureDetector.onTouchEvent(ev);
  	}
  	
  	
  	class MyGestureDetector extends SimpleOnGestureListener {
        
		@Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	System.out.println("right to left");                	
                	
                	if(index +1 < ids_list.size()){
	                	Intent intent = new Intent(getApplicationContext(),ShowRealEstate.class);
	                	intent.putExtra("id", ids_list.get(index+1));
	            		intent.putExtra("index", index+1);
	            		intent.putIntegerArrayListExtra("ids_list", ids_list);
	            		intent.putExtra("mode", mode);
	                    startActivity(intent);                   
	                    overridePendingTransition  (R.anim.right_slide_in, R.anim.left_slide_out);
	                    finish();
                	}else{
                		Toast toast = Toast.makeText(getApplicationContext(), "No more real estates to show", Toast.LENGTH_SHORT);
    	        		toast.show();
                	}
                    
          
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	System.out.println("left to right");  
                	
                	if(index -1 >= 0){
	                	Intent intent = new Intent(getApplicationContext(),ShowRealEstate.class);
	                	intent.putExtra("id", ids_list.get(index-1));
	            		intent.putExtra("index", index-1);
	            		intent.putIntegerArrayListExtra("ids_list", ids_list);
	            		intent.putExtra("mode", mode);
	                    startActivity(intent);
	                    overridePendingTransition  (R.anim.left_slide_in, R.anim.right_slide_out);
	                    
	                    finish();
                	}else{
                		Toast toast = Toast.makeText(getApplicationContext(), "No more real estates to show", Toast.LENGTH_SHORT);
    	        		toast.show();
                	}
                    
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }
    }

  	 
}
