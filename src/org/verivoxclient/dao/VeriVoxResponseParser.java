package org.verivoxclient.dao;

import java.util.function.Predicate;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class VeriVoxResponseParser {

	String results = "", totalBenchMarkCost ="", benchMarkTariff ="", benchmarkProviderName="", rank="", provider="", traiffName="" ,totalCost="";
		
	public void parseResponse(String content, Predicate<String> pre) {
		JsonElement jelement = new JsonParser().parse( content );
		JsonObject  jobject = jelement.getAsJsonObject();
		JsonArray jarray = jobject.getAsJsonArray("offers");
		
		results = jelement.getAsJsonObject().get("totalResults").getAsString();
		//JsonObject benchMark = jelement.getAsJsonObject().get("benchmarkTariff").getAsJsonObject();
		 
		 totalBenchMarkCost = jelement.getAsJsonObject().get("benchmarkTariff").getAsJsonObject().get("totalCostInEuro").getAsString();
		 benchMarkTariff = jelement.getAsJsonObject().get("benchmarkTariff").getAsJsonObject().get("tariffName").getAsJsonObject().get("content").getAsJsonObject().get("text").getAsString();
		 benchmarkProviderName = jelement.getAsJsonObject().get("benchmarkTariff").getAsJsonObject().get("providerName").getAsJsonObject().get("content").getAsJsonObject().get("text").getAsString();
	
		for(JsonElement je : jarray) {	
			provider = je.getAsJsonObject().get("provider").getAsJsonObject().get("content").getAsJsonObject().get("text").getAsString();
				if(pre.test(provider)) {
					rank = je.getAsJsonObject().get("rank").getAsString();
					totalCost = je.getAsJsonObject().get("cost").getAsJsonObject().get("totalCost").getAsJsonObject().get("amount").getAsString();
					traiffName = je.getAsJsonObject().get("tariff").getAsJsonObject().get("content").getAsJsonObject().get("text").getAsString();
					break;
				}
				provider = "";
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String delimiter = ";";
		return String.join(delimiter,  
									results, 
									rank,
									provider,
									traiffName,
									totalCost,
									benchmarkProviderName,
									benchMarkTariff,									
									totalBenchMarkCost);		
	}
}