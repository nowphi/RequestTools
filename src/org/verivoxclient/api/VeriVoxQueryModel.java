package org.verivoxclient.api;

import json.JSONObject;

@QueryModel(name = "VeriVoxQueryModel")
public class VeriVoxQueryModel {

	protected String providerName;
	
	JSONObject jsonObj = new JSONObject();
	protected String postCode, paolaTypeLocationText;
	protected int benchmarkTariffId = 0, paolaLocationId = 0;
	
	protected String profile = "H0", cancellationPeriod_unit = "week", criterion = "TotalCosts", Ascending = "Ascending";;
	
	protected boolean prePayment = false, signupOnly = true, includePackageTariffs = false, includeTariffsWithDeposit = false, includeNonCompliantTariffs = false, 
	includesNonEcoTariffs = true, onlyProductsWithGoodCustomerRating = true, onlyRegionalTariffs = false;
	
	protected int maxContractDuration = 12, maxContractProlongation = 12, minDurationInMonths = 12, annualTotal = 2000, offPeakUsage = 0, cancellationPeriod_amount = 6, 
	maxTariffsPerProvider = 1, maxResultsPerPage = 200;
		
	public VeriVoxQueryModel(String provider,  String profile, String postcode, int annualTotal, int benchmarkTariffId) {
		this.providerName = provider;
		this.postCode = postcode;
		this.profile = profile;
		this.annualTotal = annualTotal;
		this.benchmarkTariffId = benchmarkTariffId;
		this.paolaLocationId = 0;
	}
	public VeriVoxQueryModel(String provider, String profile, String postcode, int annualTotal, int benchmarkTariffId ,int paolaLocationId, String paolaTypeLocationText) {
		this.providerName = provider;
		this.postCode = postcode;
		this.profile = profile;
		this.annualTotal = annualTotal;
		this.benchmarkTariffId = benchmarkTariffId;
		this.paolaLocationId = paolaLocationId;
		this.paolaTypeLocationText = paolaTypeLocationText;
	}
	
	public int getAnnualTotal() {
		return annualTotal;
	}
	public String getPaolaTypeLocationText() {
		return paolaTypeLocationText;
	}
	public String getPostcode() {
		return postCode;
	}
	public String getUrl() {
		 return null;
	 }	
	public void setAnnualTotal(int annualTotal) {
		this.annualTotal = annualTotal;
	}
	public void setCancellationPeriod_amount(int cancellationPeriod_amount) {
		this.cancellationPeriod_amount = cancellationPeriod_amount;
	}
	public void setCancellationPeriod_unit(String cancellationPeriod_unit) {
		this.cancellationPeriod_unit = cancellationPeriod_unit;
	}
	public void setIncludeNonCompliantTariffs(boolean includeNonCompliantTariffs) {
		this.includeNonCompliantTariffs = includeNonCompliantTariffs;
	}
	public void setIncludePackageTariffs(boolean includePackageTariffs) {
		this.includePackageTariffs = includePackageTariffs;
	}
	public void setIncludesNonEcoTariffs(boolean onlyEcoTariffs) {
		this.includesNonEcoTariffs = onlyEcoTariffs;
	}
	public void setIncludeTariffsWithDeposit(boolean includeTariffsWithDeposit) {
		this.includeTariffsWithDeposit = includeTariffsWithDeposit;
	}
	public void setJsonObj(JSONObject jsonObj) {
		this.jsonObj = jsonObj;
	}
	public void setMaxContractDuration(int maxContractDuration) {
		this.maxContractDuration = maxContractDuration;
	}
	public void setMaxContractProlongation(int maxContractProlongation) {
		this.maxContractProlongation = maxContractProlongation;
	}
	public void setMaxResultsPerPage(int maxResultsPerPage) {
		this.maxResultsPerPage = maxResultsPerPage;
	}
	public void setMaxTariffsPerProvider(int maxTariffsPerProvider) {
		this.maxTariffsPerProvider = maxTariffsPerProvider;
	}
	public void setMinDurationInMonths(int minDurationInMonths) {
		this.minDurationInMonths = minDurationInMonths;
	}
	public void setOnlyProductsWithGoodCustomerRating(boolean onlyProductsWithGoodCustomerRating) {
		this.onlyProductsWithGoodCustomerRating = onlyProductsWithGoodCustomerRating;
	}
	public void setPrepayment(boolean prepayment) {
		this.prePayment = prepayment;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public void setSignupOnly(boolean signupOnly) {
		this.signupOnly = signupOnly;
	}
	public void setSorting(String sorting) {
		switch(sorting) {
			case "Preis aufsteigend": 
				criterion = "TotalCosts";
				Ascending = "Ascending";
				;break;
			case "Preis absteigend":
				criterion = "TotalCosts";
				Ascending = "Descending";
				;break;
			case "Kundenbewertung absteigend":
				criterion = "Ratings";
				Ascending = "Descending";
				;break;
			// Standardmäßig Preis aufsteigend lt. Verivox
			default:
				criterion = "TotalCosts";
				Ascending = "Ascending";
				;break;
		}
	}
	public void toJSONQuery() {
		// Profil, z. B. H0->Haushalt
		jsonObj.put("profile", profile);
		jsonObj.put("prepayment", prePayment);
		jsonObj.put("signupOnly", signupOnly);
		jsonObj.put("includePackageTariffs", includePackageTariffs);
		jsonObj.put("includeTariffsWithDeposit", includeTariffsWithDeposit);
		jsonObj.put("includeNonCompliantTariffs", includeNonCompliantTariffs);
		jsonObj.put("bonusIncluded","compliant");
		jsonObj.put("maxResultsPerPage", maxResultsPerPage);
		jsonObj.put("onlyProductsWithGoodCustomerRating", onlyProductsWithGoodCustomerRating);
		jsonObj.put("benchmarkTariffId", benchmarkTariffId);
		jsonObj.put("paolaLocationId", paolaLocationId);
								
		JSONObject jsonObj_includeEcoTariffs = new JSONObject();
		jsonObj_includeEcoTariffs.put("includesNonEcoTariffs", includesNonEcoTariffs);
		jsonObj.put("includeEcoTariffs", jsonObj_includeEcoTariffs);
												
		jsonObj.put( "maxContractDuration", maxContractDuration);
		jsonObj.put("maxContractProlongation",maxContractProlongation);

		JSONObject jsonObj_usage = new JSONObject();
		jsonObj_usage.put("annualTotal", annualTotal);
		jsonObj_usage.put("offPeakUsage", offPeakUsage);
		jsonObj.put("usage", jsonObj_usage);
		
		JSONObject jsonObj_priceGuarantee = new JSONObject();
		jsonObj_priceGuarantee.put("minDurationInMonths", minDurationInMonths);
		jsonObj.put("priceGuarantee", jsonObj_priceGuarantee);
		
		jsonObj.put( "maxTariffsPerProvider",maxTariffsPerProvider);
		
		if(cancellationPeriod_unit == null && cancellationPeriod_amount == -1) {
			jsonObj.put("cancellationPeriod", JSONObject.NULL);
		} else {
		JSONObject jsonObj_cancellationPeriod = new JSONObject();
		jsonObj_cancellationPeriod.put("unit", cancellationPeriod_unit);
		jsonObj_cancellationPeriod.put("amount", cancellationPeriod_amount);
		jsonObj.put("cancellationPeriod", jsonObj_cancellationPeriod);	
		}
		
		jsonObj.put( "previewDisplayTime", JSONObject.NULL);
		
		jsonObj.put("onlyRegionalTariffs", onlyRegionalTariffs);

		JSONObject sorting = new JSONObject();
		sorting.put("criterion", criterion);
		sorting.put("direction", Ascending);
		jsonObj.put("sorting", sorting);	
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return jsonObj.toString();
	}
}
