package org.verivoxclient.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.verivoxclient.model.VeriVoxResponseModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class VeriVoxResponseParser {

	String results = "", totalBenchMarkCost ="", benchMarkTariff ="", benchmarkProviderName="", rank="", provider="", traiffName="" ,totalCost="";
	
	List<VeriVoxResponseModel> response = new ArrayList<VeriVoxResponseModel>();
	
	public List<VeriVoxResponseModel>  parseResponse(String content, Predicate<String> pre) {
		JsonElement jelement = new JsonParser().parse( content );
		JsonObject  jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("offers");
		
		results = jelement.getAsJsonObject().get("totalResults").getAsString();
		
		 totalBenchMarkCost = jelement.getAsJsonObject().get("benchmarkTariff").getAsJsonObject().get("totalCostInEuro").getAsString();
		 benchMarkTariff = jelement.getAsJsonObject().get("benchmarkTariff").getAsJsonObject().get("tariffName").getAsJsonObject().get("content").getAsJsonObject().get("text").getAsString();
		 benchmarkProviderName = jelement.getAsJsonObject().get("benchmarkTariff").getAsJsonObject().get("providerName").getAsJsonObject().get("content").getAsJsonObject().get("text").getAsString();
	
		for(JsonElement je : jarray) {	
			provider = je.getAsJsonObject().get("provider").getAsJsonObject().get("content").getAsJsonObject().get("text").getAsString();
				if(pre.test(provider)) {
								
					rank = je.getAsJsonObject().get("rank").getAsString();
					totalCost = je.getAsJsonObject().get("cost").getAsJsonObject().get("totalCost").getAsJsonObject().get("amount").getAsString();
					traiffName = je.getAsJsonObject().get("tariff").getAsJsonObject().get("content").getAsJsonObject().get("text").getAsString();
					
					VeriVoxResponseModel match = new VeriVoxResponseModel( results,  totalBenchMarkCost,  benchMarkTariff,
							 benchmarkProviderName,  rank,  provider,  traiffName,  totalCost);
					
					response.add(match);
				}
				provider = "";
		}
		
		// DummyObjekt, falls kein Match (Informativ)
		if(response.isEmpty() == true) {
			VeriVoxResponseModel match = new VeriVoxResponseModel( results,  totalBenchMarkCost,  benchMarkTariff,
					 benchmarkProviderName,  rank,  provider,  traiffName,  totalCost);
			response.add(match);
		}
		
		return response;
	}
		
	public List<VeriVoxResponseModel> getResponseModel() {
		return response;
	}
}