package org.verivoxclient.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.verivox.model.VeriVoxQueryAll;
import org.verivox.model.VeriVoxQueryModel;
import org.verivox.model.VeriVoxQueryRecommended;
import org.verivoxclient.dao.HttpsVeriVoxClient;
import org.verivoxclient.dao.IHttpsVeriVoxClient;
import org.verivoxclient.dao.LocationPropertiesThread;
import org.verivoxclient.dao.VeriVoxOffersRequestThread;
import org.verivoxclient.dao.VeriVoxThread;

import json.JSONObject;


public class VeriVoxRequestController {

	private IHttpsVeriVoxClient vc;
	
	private List<String> PostCodeList = new ArrayList<String>();
	private List<LocationPropertiesThread> LocationPropertiesList  = new ArrayList<LocationPropertiesThread>();
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
	
		LocationPropertiesThread lp = null;
		for (Iterator<String> it = PostCodeList.listIterator(); it.hasNext();) {
			postcode = it.next();
			
			List<String> paolaTypeIDList = vc.requestPostCodeValue(postcode);
			
			if(paolaTypeIDList.size() == 0) {
				lp = new LocationPropertiesThread(postcode, annualTotal, "0", "Kein OT");
				lp.start();
				LocationPropertiesList.add(lp);
			} else {
				for (Iterator<String> itert = paolaTypeIDList.listIterator(); itert.hasNext();) {
					
					JSONObject jo = new JSONObject(itert.next());
				
					String paolaType = jo.getString("value");
					String paolaText = jo.getString("text");
					
					lp = new LocationPropertiesThread(postcode, annualTotal, paolaType, paolaText);
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
		 LocationPropertiesThread lp = null;
		
		for(Iterator<LocationPropertiesThread> threadorator = LocationPropertiesList.listIterator(); threadorator.hasNext();) {
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
