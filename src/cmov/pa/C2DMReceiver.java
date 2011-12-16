/***
  Copyright (c) 2008-2011 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Advanced Android Development_
    http://commonsware.com/AdvAndroid
*/

package cmov.pa;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cmov.pa.utils.HouseInfo;

import com.google.android.c2dm.C2DMBaseReceiver;
import com.google.android.c2dm.C2DMessaging;

public class C2DMReceiver extends C2DMBaseReceiver {
	
	Api api;
	
	
	public C2DMReceiver() {  
		super(Api.c2dmAccount);
		
		api = new Api();
		
	}

	@Override
	public void onRegistered(Context context, String registrationId) {
		Log.w("C2DMReceiver-onRegistered", registrationId);
		
		
		//TODO:enviar key pro server
		api.registerKey(C2DMessaging.getRegistrationId(this));
		ArrayList<HouseInfo> houses;
		try {
			if(api.getLastUpdateDate().equalsIgnoreCase("")){//primeira vez;
					houses = api.updateList(null);
				
				
			}else{
					houses = api.updateList(api.getLastUpdateDate());
					
					//verificar se algum foi modificado
					//nao mostrar repetidos

			}
			
			//TODO: criar intent e lancar intent

			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
  
	@Override
	public void onUnregistered(Context context) {
		Log.w("C2DMReceiver-onUnregistered", "got here!");
	}
  
	@Override
	public void onError(Context context, String errorId) {
		Log.w("C2DMReceiver-onError", errorId);
	}
  
	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.w("C2DMReceiver",intent.getStringExtra("data.operation") + " " + intent.getStringExtra("data.property_kind") + " " + intent.getStringExtra("data.property_city") + " "+ intent.getStringExtra("data.property_id"));
	
		
		boolean display = api.updateNotificationPendingLists(intent.getStringExtra("data.operation"),
				Integer.parseInt(intent.getStringExtra("data.property_id")),	
				intent.getStringExtra("data.property_kind"),
				intent.getStringExtra("data.property_city"));
		
		
		if(display)
			api.displayNotificationMessage(this);
		
	}
}