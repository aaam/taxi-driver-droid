package com.catglo.deliveryDatabase;

import java.util.ArrayList;

import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.util.Log;

import com.catglo.deliveryDatabase.AddressSuggestiorGoogle.AddressSuggestionCommitor;


public class SilentAddressValidator {
	AddressSuggestiorGoogle webService;
	Context context;
	public SilentAddressValidator(Context context){
		 webService = new AddressSuggestiorGoogle(context,null); 
		 this.context = context;
	}
	public void lookup(final Order order){
		
		webService.commitor = new AddressSuggestionCommitor(){public void commit(ArrayList<AddressInfo> addresses, String originalSearchString) {
			//if the case insensitive match for the first part of the address matches then correct the address and set the geopoint and save to the db
			if (addresses.size() >0){
				if (addresses.get(0).address.toLowerCase().startsWith(order.address.toLowerCase())){
					order.address = addresses.get(0).address;
					order.geoPoint = addresses.get(0).location;
					order.isValidated = true;
				
					float lat = (float)order.geoPoint.getLatitudeE6()/(float)1E6;
					float lng = (float)order.geoPoint.getLongitudeE6()/(float)1E6;
				
					Log.i("Taxi","Validated at lat="+lat+"  lng="+lng);
					DataBase d = new DataBase(context);
					d.open();
					d.edit(order);
					d.close();
					
				}
			}
		}};
		webService.lookup(order.address);
	}
}
