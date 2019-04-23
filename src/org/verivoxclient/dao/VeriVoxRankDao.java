package org.verivoxclient.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.verivoxclient.controller.HttpsVeriVoxClient;
import org.verivoxclient.controller.IHttpsVeriVoxClient;
import org.verivoxclient.controller.LocationPropertiesThread;
import org.verivoxclient.controller.VeriVoxOffersRequestThread;
import org.verivoxclient.controller.VeriVoxThread;
import org.verivoxclient.model.VeriVoxQueryAll;
import org.verivoxclient.model.VeriVoxQueryModel;
import org.verivoxclient.model.VeriVoxQueryRecommended;

import json.JSONObject;

public class VeriVoxRankDao implements IVeriVoxRankDao {

	private List<LocationPropertiesThread> LocationPropertiesList  = new ArrayList<LocationPropertiesThread>();
	
	private List<String> PostCodeList = new ArrayList<String>();
	private IHttpsVeriVoxClient vc;
	private List<VeriVoxOffersRequestThread> VeriVoxOffersRequestThread  = new ArrayList<VeriVoxOffersRequestThread>();
	
	public VeriVoxRankDao(List<String> PostCodeList) {
		vc = new HttpsVeriVoxClient();
		this.PostCodeList = PostCodeList;
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
				
					String paolaType = jo.getString("id");
					String paolaText = jo.getString("partName");
					
					lp = new LocationPropertiesThread(postcode, annualTotal, paolaType, paolaText);
					lp.start();
					LocationPropertiesList.add(lp);
				}		
			}
		}
		// joint erzeugten Threads zum MasterThread
		joinThreads(LocationPropertiesList);
	}
	
	public int getPredictedTime() {
		return LocationPropertiesList.size();
	}
		
	public int getSizePostCodeLocationList() {
		return LocationPropertiesList.size();
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
	
	@Override
	public List<VeriVoxOffersRequestThread> requestPostCodeList(int modus, String provider) {
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
			default: return null;
			}
			
			requestThread = new VeriVoxOffersRequestThread(VM, provider);
			requestThread.start();
			
			// Threads werden gelistet in ...
			VeriVoxOffersRequestThread.add(requestThread);
		}
		// joint erzeugten Threads zum MasterThread	
		this.joinThreads(  VeriVoxOffersRequestThread);
		
		return VeriVoxOffersRequestThread;
	}	
}
