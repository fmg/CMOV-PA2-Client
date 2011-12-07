package cmov.pa;

import java.util.LinkedList;

import cmov.pa.utils.HouseInfo;

import android.app.Application;
import android.app.ListActivity;

public class Api extends Application{
	
	public LinkedList<HouseInfo> list;
	
	public Api(){
		list = new LinkedList<HouseInfo>();
	}
	
	public void RegisterKey(String key){
		
	}

}
