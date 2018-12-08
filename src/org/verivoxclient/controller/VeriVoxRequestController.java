package org.verivoxclient.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.verivoxclient.api.HttpsVeriVoxClient;
import org.verivoxclient.api.IHttpsVeriVoxClient;
import org.verivoxclient.api.LocationProperties;
import org.verivoxclient.api.VeriVoxOffersRequestThread;
import org.verivoxclient.api.VeriVoxQueryAll;
import org.verivoxclient.api.VeriVoxQueryModel;
import org.verivoxclient.api.VeriVoxQueryRecommended;
import org.verivoxclient.api.VeriVoxThread;

import json.JSONObject;


public class VeriVoxRequestController {

	private IHttpsVeriVoxClient vc;
	
	private List<String> PostCodeList = new ArrayList<String>();
	private List<LocationProperties> LocationPropertiesList  = new ArrayList<LocationProperties>();
	private List<VeriVoxOffersRequestThread> VeriVoxOffersRequestThread  = new ArrayList<VeriVoxOffersRequestThread>();
	int predictedTime;
	
	public VeriVoxRequestController(List<String> PostCodeList) {
		vc = new HttpsVeriVoxClient();
		this.PostCodeList = PostCodeList;
	}
	
	public int getSizePostCodeLocationList() {
		return LocationPropertiesList.size();
	}
	
	public int getPredictedTime() {
		return LocationPropertiesList.size();
	}
	
	
	public void createListLocationProperties(int annualTotal) {
		String postcode;
	
		LocationProperties lp = null;
		for (Iterator<String> it = PostCodeList.listIterator(); it.hasNext();) {
			postcode = it.next();
			
			List<String> paolaTypeIDList = vc.requestPostCodeValue(postcode);
			
			if(paolaTypeIDList.size() == 0) {
				lp = new LocationProperties(postcode, annualTotal, "0", "Kein OT");
				lp.start();
				LocationPropertiesList.add(lp);
			} else {
				for (Iterator<String> itert = paolaTypeIDList.listIterator(); itert.hasNext();) {
					
					JSONObject jo = new JSONObject(itert.next());
				
					String paolaType = jo.getString("value");
					String paolaText = jo.getString("text");
					
					lp = new LocationProperties(postcode, annualTotal, paolaType, paolaText);
					lp.start();
					LocationPropertiesList.add(lp);
				}		
			}
		}
		// joint erzeugten Threads zum MasterThread
		joinThreads(LocationPropertiesList);
	}
		
	public List<VeriVoxOffersRequestThread> getVeriVoxOffersRequestThread() {
		return VeriVoxOffersRequestThread;
	}
		
	private void joinThreads(@SuppressWarnings("rawtypes") List list) {
		for(@SuppressWarnings("rawtypes")
		Iterator threadorator = list.listIterator(); threadorator.hasNext();) {
			Thread tr = ( (VeriVoxThread) threadorator.next()).WorkingThread();
			if(tr.isAlive() == true) {
				try {
					tr.join();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
	}
		
	public void requestPostCodeList(int modus, String provider) {
		VeriVoxOffersRequestThread requestThread;
		 VeriVoxQueryModel VM = null;
		LocationProperties lp = null;
		
		for(Iterator<LocationProperties> threadorator = LocationPropertiesList.listIterator(); threadorator.hasNext();) {
			lp = threadorator.next();
	
			switch(modus) {
			case 0: ; 
				break; //VM = new VeriVoxQueryRecommended( profile, postcode, annualTotal, benchmarkTariffId, paolaTypeLocationId, paolaTypeLocationText);
			case 1:  
				VM = new VeriVoxQueryRecommended(provider, "H0", lp.getPostCode(), lp.annualTotal(), lp.benchmarkTariffId(), 
						lp.getPaolaTypeLocationId(), lp.getPaolaTypeLocationText()); 
				break;
			case 2:  
				VM = new VeriVoxQueryAll(provider,  "H0", lp.getPostCode(), lp.annualTotal(), lp.benchmarkTariffId(), 
						lp.getPaolaTypeLocationId(), lp.getPaolaTypeLocationText()); 
				break;
			default: return;
			}
			
			requestThread = new VeriVoxOffersRequestThread(VM, provider);
			requestThread.start();
			
			// Threads werden gelistet in ...
			VeriVoxOffersRequestThread.add(requestThread);
		}
		// joint erzeugten Threads zum MasterThread	
		this.joinThreads(  VeriVoxOffersRequestThread);
	}	
}
