package cmov.pa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.ViewFlipper;

public class ShowRealEstate  extends Activity {
	
	 private static final int SWIPE_MIN_DISTANCE = 100;
	 private static final int SWIPE_MAX_OFF_PATH = 150;
	 private static final int SWIPE_THRESHOLD_VELOCITY = 150;
	 private GestureDetector gestureDetector;
	 View.OnTouchListener gestureListener;
	 
	 int scroll = 0;
	 private final int scrollDesta = 20;
	 int width;
	 int height;
	 
	
  	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_real_estate);
        
        gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };
        
        Display display = getWindowManager().getDefaultDisplay(); 
        width = display.getWidth();
        height = display.getHeight();
        
        //System.out.println(width + " " + height);
        
        //((RelativeLayout)findViewById(R.id.RelativeLayout1)).scrollTo(0, 0);

    }
  	
  	
  	class MyGestureDetector extends SimpleOnGestureListener {
        @Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
        	
        	RelativeLayout r = (RelativeLayout)findViewById(R.id.RelativeLayout1);
        	if(e1.getY() > e2.getY()){
        		scroll -= 20;
        		//System.out.println("scroll up");
        		if(scroll <= 0){
        			r.scrollTo(0, 0);
        			scroll = 0;
        		}else{
        			r.scrollTo(0, scroll);
        		}
        	}else{
        		scroll+= 20;
        		//System.out.println("scroll down");
        		if(scroll >= Math.abs(r.getHeight()- height)){
        			r.scrollTo(0, Math.abs(r.getHeight()- height));
        			scroll = Math.abs(r.getHeight()- height);
        		}else{
        			r.scrollTo(0, scroll);
        		}
        		
        	}
        	
        	System.out.println(r.getHeight());
        	System.out.println(r.getWidth());
        	//System.out.println("baseline-> " + r.getBaseline());
        	//System.out.println("bottom-> " +r.getBottom());

			return super.onScroll(e1, e2, distanceX, distanceY);
		}

		@Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	System.out.println("right to left");                	
                	Intent intent = new Intent(getApplicationContext(),ShowRealEstate.class);
                    startActivity(intent);
                    overridePendingTransition  (R.anim.right_slide_out, R.anim.right_slide_in);
                    finish();
          
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	System.out.println("left to right");                	
                	Intent intent = new Intent(getApplicationContext(),ShowRealEstate.class);
                    startActivity(intent);
                    overridePendingTransition  (R.anim.right_slide_in, R.anim.right_slide_out);
                    finish();
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }
    }
  	
  	
  	 @Override
     public boolean onTouchEvent(MotionEvent event) {
         if (gestureDetector.onTouchEvent(event))
 	        return true;
 	    else
 	    	return false;
     }
  	 
}
