package org.verivoxclient.model;

@QueryModel(name = "VeriVoxQueryModel") 
public class VeriVoxQueryRecommended extends VeriVoxQueryModel {

	private final String recommendedPath = "https://www.verivox.de/servicehook/offers/electricity/postCode/";
	
	public VeriVoxQueryRecommended(String provider, String profile, String postcode, int annualTotal, int benchmarkTariffId ,int paolaLocationId, String paolaTypeLocationText) {
		// TODO Auto-generated constructor stub
		super( provider, profile, postcode,  annualTotal,  benchmarkTariffId , paolaLocationId, paolaTypeLocationText);
	}
	
	public VeriVoxQueryRecommended(String provider,String profile, String postcode, int annualTotal, int benchmarkTariffId) {
		// TODO Auto-generated constructor stub
		super( provider, profile, postcode, annualTotal,  benchmarkTariffId);
	}

	@Override
	public String getUrl() {
		 return recommendedPath + this.postCode + "/" + "recommended";
	 }
}
