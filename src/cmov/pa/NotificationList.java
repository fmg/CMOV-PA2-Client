package cmov.pa;

import java.util.ArrayList;

import cmov.pa.utils.HouseInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NotificationList extends Activity{

	MyListAdapter newAdapter;
	MyListAdapter updateAdapter;
	Api api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_notifications);
        
        api = (Api)getApplicationContext();
        
        api.count=1;
        
        
        newAdapter = new MyListAdapter(api.new_list, R.drawable.new_icon);
        
        updateAdapter = new MyListAdapter(api.updated_list, R.drawable.new_icon);
        
        ((ListView)findViewById(R.id.listView1)).setAdapter(newAdapter);
        ((ListView)findViewById(R.id.listView2)).setAdapter(updateAdapter);
		
		
		if(newAdapter.getCount() == 0){
			((RelativeLayout)findViewById(R.id.list_notification_new_container)).setVisibility(View.GONE);
		}
		
		if(updateAdapter.getCount() == 0){
			((RelativeLayout)findViewById(R.id.list_notification_updated_container)).setVisibility(View.GONE);
		}
		
		
		 ((ListView)findViewById(R.id.listView1)).setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				ArrayList<Integer> tmp = newAdapter.getIdList();
				
				Intent intent = new Intent(getApplicationContext(),ShowRealEstate.class);
				intent.putExtra("index", arg2);
				intent.putExtra("mode", api.MODE_NEW);
				intent.putIntegerArrayListExtra("ids_list", tmp);
		        startActivity(intent);
				
			}
		});
		 
		 
	     ((ListView)findViewById(R.id.listView2)).setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				ArrayList<Integer> tmp = updateAdapter.getIdList();
				
				Intent intent = new Intent(getApplicationContext(),ShowRealEstate.class);
				intent.putExtra("index", arg2);
				intent.putExtra("mode", api.MODE_UPDATE);
				intent.putIntegerArrayListExtra("ids_list", tmp);
	
		        startActivity(intent);
				
			}
		});
			
    }
    
    @Override
	public void onBackPressed() {
		api.new_list = new ArrayList<HouseInfo>();
		api.updated_list = new ArrayList<HouseInfo>();
		
		super.onBackPressed();
	}

	
	public class MyListAdapter extends BaseAdapter {

    	ArrayList<HouseInfo> list;
    	int image_id;
    	
    	
    	public MyListAdapter(ArrayList<HouseInfo> list, int image_id){
    		this.list = list;
    		this.image_id = image_id;
    		
    	}
    	
    	public ArrayList<Integer> getIdList(){
    		ArrayList<Integer> l = new ArrayList<Integer>();
    		for(HouseInfo h:list)
    			l.add(h.getId());
    		
    		return l;
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
        		 
        		 
				((ImageView)convertView.findViewById(R.id.list_child_image)).setImageResource(image_id);
	
				((TextView) convertView.findViewById(R.id.list_child_address)).setText("");
				
				 ((TextView) convertView.findViewById(R.id.list_child_bedrooms)).setText(house.getKind());
				 ((TextView) convertView.findViewById(R.id.list_child_city)).setText(house.getCity());
				 
			return convertView;
		}
	}
}
