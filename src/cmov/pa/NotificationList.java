package cmov.pa;

import java.util.ArrayList;

import cmov.pa.CMOVPA2Activity.MyListAdapter;
import cmov.pa.utils.HouseInfo;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NotificationList extends ListActivity{
	
	MyListAdapter mAdapter;
	Api api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_notifications);
        
        api = (Api)getApplicationContext();
        
        api.count=1;
        
        
        mAdapter = new MyListAdapter();
		setListAdapter(mAdapter);
		
		
		api.displayNotificationMessage(this);
		api.displayNotificationMessage(this);

    }
    
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent(getApplicationContext(),ShowRealEstate.class);
        startActivity(intent);
	}

    
	
	public class MyListAdapter extends BaseAdapter {

    	ArrayList<HouseInfo> list;
    	
    	
    	public MyListAdapter(){
    		list = new ArrayList<HouseInfo>();
    		
    	}
    	
		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {    
			
            	 LayoutInflater infalInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        		 convertView = infalInflater.inflate(R.layout.list_child, null);
        		 
        		 HouseInfo house = list.get(position);
        		 
        		 System.out.println(house.getAddress());
        		 
        		 ((TextView) convertView.findViewById(R.id.list_child_address)).setText(house.getAddress());
				 
				
				if(!house.isFor_sale())
					((RelativeLayout)convertView.findViewById(R.id.list_child_layout)).setBackgroundResource(R.drawable.background_sold);
				else
					((RelativeLayout)convertView.findViewById(R.id.list_child_layout)).setBackgroundResource(R.drawable.background_selling);
				 
				 
				 if(house.getKind().equalsIgnoreCase("Flat"))
					 ((ImageView)convertView.findViewById(R.id.list_child_image)).setImageResource(R.drawable.flat_icon);
				 else if(house.getKind().equalsIgnoreCase("House"))
					 ((ImageView)convertView.findViewById(R.id.list_child_image)).setImageResource(R.drawable.house_icon);
				 else
					 ((ImageView)convertView.findViewById(R.id.list_child_image)).setImageResource(R.drawable.castle_icon);
	
				 
				
				 ((TextView) convertView.findViewById(R.id.list_child_bedrooms)).setText("T"+house.getBedrooms());
				 ((TextView) convertView.findViewById(R.id.list_child_city)).setText(house.getCity());
				 
			return convertView;
		}
	}
}
