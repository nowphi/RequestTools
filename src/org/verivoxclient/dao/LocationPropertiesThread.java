package org.verivoxclient.dao;

public class LocationPropertiesThread extends VeriVoxThread {

	String PostCode, paolaTypeLocationId, paolaTypeLocationText;
	int benchmarkTariffId, annualTotal;
		
	public LocationPropertiesThread(String PostCode, String paolaType, int benchmarkTariffId) {
		super();
		this.PostCode = PostCode;
		this.benchmarkTariffId = benchmarkTariffId;
		this.paolaTypeLocationId = paolaType;
	}
	
	public LocationPropertiesThread(String postcode, int annualTotal, String paolaType, String paolaText) {	
		super();
		this.PostCode = postcode;
		this.annualTotal = annualTotal;
		this.paolaTypeLocationId = paolaType;
		this.paolaTypeLocationText = paolaText;		
	}
	
	public String getPostCode() {
		return PostCode;
	}
	
	public int getPaolaTypeLocationId() {
		return Integer.parseInt(paolaTypeLocationId);
	}
	
	public String getPaolaTypeLocationText() {
		return paolaTypeLocationText;
	}
	
	public int benchmarkTariffId() {
		return benchmarkTariffId;
	}

	public int annualTotal() {
		return annualTotal;
	}
	@Override
	public String toString() {
		String delimiter = " ";
		return String.join(delimiter, PostCode, paolaTypeLocationId, Integer.toString(benchmarkTariffId));
	}	
	@Override
	public void run()  {
		benchmarkTariffId = VeriVoxClient.requestBenchmarkTarifID(annualTotal, PostCode, paolaTypeLocationId);
	}
}
