package cmov.pa;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class ShowRealEstate  extends Activity implements Runnable{
	
	 private static final int SWIPE_MIN_DISTANCE = 100;
	 private static final int SWIPE_MAX_OFF_PATH = 150;
	 private static final int SWIPE_THRESHOLD_VELOCITY = 150;
	 private GestureDetector gestureDetector;
	 View.OnTouchListener gestureListener;
	 
	 
	 ArrayList<Integer> ids_list;
	 int index ;
	 String mode;
	
  	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_real_estate);
        
        
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

    }
  	
  	@Override
	public void run() {
		
  		handler.sendMessage(handler.obtainMessage());
		
	}
  	
  	
  	final Handler handler = new Handler() {
        public void handleMessage(Message msg) {

        	
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
