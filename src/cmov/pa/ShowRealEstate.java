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

    }

  	
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
                	Intent intent = new Intent(getApplicationContext(),ShowRealEstate.class);
                    startActivity(intent);                   
                    overridePendingTransition  (R.anim.right_slide_in, R.anim.left_slide_out);
                    finish();
                    
          
                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                	System.out.println("left to right");                	
                	Intent intent = new Intent(getApplicationContext(),ShowRealEstate.class);
                    startActivity(intent);
                    overridePendingTransition  (R.anim.left_slide_in, R.anim.right_slide_out);
                    
                    finish();
                    
                }
            } catch (Exception e) {
                // nothing
            }
            return false;
        }
    }
  	
  	
  	 
  	 
}
