package cmov.pa;


import java.util.ArrayList;

import com.google.android.c2dm.C2DMessaging;

import cmov.pa.database.DatabaseAdapter;
import cmov.pa.utils.HouseInfo;

import android.app.ListActivity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CMOVPA2Activity extends ListActivity {

	MyListAdapter mAdapter;
	Api api;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_real_estates);
        
        api = new Api();
        
        api.dbAdapter = new DatabaseAdapter(getApplicationContext());
        
        
        api.mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        mAdapter = new MyListAdapter();
		setListAdapter(mAdapter);
		
		
		String id = C2DMessaging.getRegistrationId(this);
		//se ainda nao estiver registado, regista-se
		if(id.equals("")){
			System.out.println("Vai obter a chave pela 1ra vez");
			C2DMessaging.register(this, api.c2dmAccount);
			
		//TODO: enviar coisas pro server
		}else{
			System.out.println("registration id ->" + id);
			if(C2DMessaging.getLastRegistrationChange(this) > 0){
				System.out.println("Vai obter a chave nova");
				
				//TODO: enciar coisas pro server;
				//pesquizar por pela data do ultimo update
				//verificar se algum foi modificado
				//nao mostrar repetidos
			}
		}
		
	
		
    }
    
    
    
    @Override
	protected void onResume() {
    	super.onResume();
    	
    	mAdapter.setList(api.getFavourites());
    	mAdapter.notifyDataSetChanged();
	}
    
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
						
		Intent intent = new Intent(getApplicationContext(),ShowRealEstate.class);
		intent.putExtra("mode", api.MODE_FAVOURITE);
		intent.putIntegerArrayListExtra("ids_list", mAdapter.getListIds());
		intent.putExtra("index", position);
        startActivity(intent);
	}


    
    
    
    public class MyListAdapter extends BaseAdapter {

    	ArrayList<HouseInfo> list;
    	
    	
    	public MyListAdapter(){
    		list = new ArrayList<HouseInfo>();
    		
    	}
    	
    	public void setList(ArrayList<HouseInfo> l){
    		list = l;
    	}
    	
    	
    	public ArrayList<Integer> getListIds(){
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
        		 
        		 System.out.println(house.getAddress());
        		 
        		 ((TextView) convertView.findViewById(R.id.list_child_address)).setText(house.getAddress());
				 
				
				if(!house.isFor_sale())
					((RelativeLayout)convertView.findViewById(R.id.list_child_layout)).setBackgroundResource(R.drawable.background_sold);
				else
					((RelativeLayout)convertView.findViewById(R.id.list_child_layout)).setBackgroundResource(R.drawable.background_selling);
				 
				 
				 if(house.getKind().equalsIgnoreCase("Apartment"))
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
