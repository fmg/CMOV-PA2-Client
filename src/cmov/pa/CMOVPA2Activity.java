package cmov.pa;

import java.util.ArrayList;

import cmov.pa.utils.HouseInfo;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CMOVPA2Activity extends ListActivity {
	
	
	MyListAdapter mAdapter;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_real_estates);
        
        
        mAdapter = new MyListAdapter();
		setListAdapter(mAdapter);
    }
    
    
    
    public class MyListAdapter extends BaseAdapter {

    	ArrayList<HouseInfo> list;
    	
    	
    	public MyListAdapter(){
    		list = new ArrayList<HouseInfo>();
    		
    		//TODO: deiscutir se vale a pena guardar o estado do imovel
    		
    		//TODO: apagar
    		HouseInfo h1 = new HouseInfo(1, "apartamento", "rua over 9000", "Porto", 4, "venda");
    		HouseInfo h2 = new HouseInfo(2, "moradia", "rua do alem", "Vila Real", 8, "venda");
    		HouseInfo h3 = new HouseInfo(3, "castelo", "rua americana", "Dubai", 30, "vendida");  		
    		
    		list.add(h1);
    		list.add(h2);
    		list.add(h3);
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
				 
				
				if(house.getState().equalsIgnoreCase("vendida"))
					((RelativeLayout)convertView.findViewById(R.id.list_child_layout)).setBackgroundResource(R.drawable.background_sold);
				else
					((RelativeLayout)convertView.findViewById(R.id.list_child_layout)).setBackgroundResource(R.drawable.background_selling);
				 
				 
				 if(house.getType().equalsIgnoreCase("apartamento"))
					 ((ImageView)convertView.findViewById(R.id.list_child_image)).setImageResource(R.drawable.flat_icon);
				 else if(house.getType().equalsIgnoreCase("moradia"))
					 ((ImageView)convertView.findViewById(R.id.list_child_image)).setImageResource(R.drawable.house_icon);
				 else
					 ((ImageView)convertView.findViewById(R.id.list_child_image)).setImageResource(R.drawable.castle_icon);
	
				 
				
				 ((TextView) convertView.findViewById(R.id.list_child_bedrooms)).setText("T"+house.getBedrooms());
				 ((TextView) convertView.findViewById(R.id.list_child_city)).setText(house.getCity());
				 
			return convertView;
		}
	}
}
