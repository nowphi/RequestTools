package org.verivoxclient.model;

public class VeriVoxResponseModel {

	String results = "", totalBenchMarkCost ="", benchMarkTariff ="", benchmarkProviderName="", rank="", provider="", traiffName="" ,totalCost="";
	
	public VeriVoxResponseModel(String results, String totalBenchMarkCost, String benchMarkTariff,
			String benchmarkProviderName, String rank, String provider, String traiffName, String totalCost) {
		super();
		this.results = results;
		this.totalBenchMarkCost = totalBenchMarkCost;
		this.benchMarkTariff = benchMarkTariff;
		this.benchmarkProviderName = benchmarkProviderName;
		this.rank = rank;
		this.provider = provider;
		this.traiffName = traiffName;
		this.totalCost = totalCost;
	}

	public String getResults() {
		return results;
	}

	public String getTotalBenchMarkCost() {
		return totalBenchMarkCost;
	}

	public String getBenchMarkTariff() {
		return benchMarkTariff;
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

	public String getBenchmarkProviderName() {
		return benchmarkProviderName;
	}

	public String getRank() {
		return rank;
	}

	public String getProvider() {
		return provider;
	}

	public String getTraiffName() {
		return traiffName;
	}

	public String getTotalCost() {
		return totalCost;
	}
}
