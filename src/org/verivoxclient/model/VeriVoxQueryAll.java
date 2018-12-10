package org.verivoxclient.model;

@QueryModel(name = "VeriVoxQueryAll")
public class VeriVoxQueryAll extends VeriVoxQueryModel {

	private final String allPath = "https://www.verivox.de/servicehook/offers/electricity/postCode/";

	/**
	 * public VeriVoxQueryAll(String profile, String postcode, int annualTotal, int benchmarkTariffId, int paolaLocationId, String paolaTypeLocationText)
	 * <br><br>
	 * @param profile
	 * @param postcode
	 * @param annualTotal
	 * @param benchmarkTariffId
	 * @param paolaLocationId
	 * @param paolaTypeLocationText
	 */
	public VeriVoxQueryAll( String provider, String profile, String postcode, int annualTotal, int benchmarkTariffId, int paolaLocationId, String paolaTypeLocationText) {
		// TODO Auto-generated constructor stub
		super(provider, profile, postcode, annualTotal, benchmarkTariffId, paolaLocationId, paolaTypeLocationText);

		// Zusätzlich für All
				this.setSignupOnly(false);
				this.setPrepayment(true);
				this.setIncludePackageTariffs(true);
				this.setIncludeTariffsWithDeposit(true);
				this.setIncludeNonCompliantTariffs(true);
				this.setMaxResultsPerPage(999);
				this.setOnlyProductsWithGoodCustomerRating(false);
				this.setMaxContractDuration(240);
				this.setMaxContractProlongation(240);
				this.setMinDurationInMonths(0);
				this.setMaxTariffsPerProvider(999);
				this.setCancellationPeriod_unit(null);
				this.setCancellationPeriod_amount(-1);
				this.setIncludesNonEcoTariffs(true);
		
	}

	/**
	 * public VeriVoxQueryAll(String profile, String postcode, int annualTotal, int benchmarkTariffId, String paolaTypeLocationText)
	 * <br><br>
	 * @param profile
	 * @param postcode
	 * @param annualTotal
	 * @param benchmarkTariffId
	 * @param paolaTypeLocationText
	 */
	public VeriVoxQueryAll(String provider, String profile, String postcode, int annualTotal, int benchmarkTariffId, String paolaTypeLocationText) {
		// TODO Auto-generated constructor stub
		super(provider, profile, postcode, annualTotal, benchmarkTariffId);
		
		// Zusätzlich für All
		this.setSignupOnly(false);
		this.setIncludePackageTariffs(true);
		this.setPrepayment(true);
		this.setIncludeTariffsWithDeposit(true);
		this.setIncludeNonCompliantTariffs(true);
		this.setMaxResultsPerPage(999);
		this.setOnlyProductsWithGoodCustomerRating(false);
		this.setMaxContractDuration(240);
		this.setMaxContractProlongation(240);
		this.setMinDurationInMonths(0);
		this.setMaxTariffsPerProvider(999);
		this.setCancellationPeriod_unit(null);
		this.setCancellationPeriod_amount(-1);
		this.setIncludesNonEcoTariffs(true);
		
		
		
	}
	
	@Override
	public String getUrl() {
		 return allPath + this.postCode + "/" + "all";
	 }
}
