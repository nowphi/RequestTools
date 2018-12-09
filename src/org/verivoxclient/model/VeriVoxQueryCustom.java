package org.verivoxclient.model;

@QueryModel(name = "VeriVoxQueryCustom")
public class VeriVoxQueryCustom extends VeriVoxQueryModel {

	public void setVertragsLaufzeit(String VertragsLaufzeit) {
		switch(VertragsLaufzeit) {
			case "3M": 
				maxContractDuration = 3;
				;break;
			case "6M":
				maxContractDuration = 6;
				break;
			case "12M":
				maxContractDuration = 12;
				break;
			case "24M":
				maxContractDuration = 24;
				;break;
			// ... enspricht egal
			default:
				maxContractDuration = 240;
				;break;	
			}
	}
	
	public void setVertragsVerlaengerung(String Verlaengerung) {
	
		switch(Verlaengerung) {
			case "1M": 
				maxContractProlongation = 1;
				;break;
			case "3M":
				maxContractProlongation = 3;
				;break;
			case "6M":
				maxContractProlongation = 6;
			case "12M":
				maxContractProlongation = 12;
				;break;
			// ... enspricht egal
			default:
				maxContractProlongation = 240;
			;break;
		}
	}
	
	public void setKuendigungsfrist(String Kuendigungsfrist) {
		switch(Kuendigungsfrist) {	
			case "1M": 
				cancellationPeriod_unit = "month";
				cancellationPeriod_amount =1;
				;break;
			case "2M":
				cancellationPeriod_unit = "month";
				cancellationPeriod_amount = 2;
				;break;
			case "3M":
				cancellationPeriod_unit = "month";
				cancellationPeriod_amount =3;
			case "6W":
				cancellationPeriod_unit = "week";
				cancellationPeriod_amount = 6;
				;break;
				// ... enspricht egal
			default:
				cancellationPeriod_unit = null;
				cancellationPeriod_amount = -1;
		}
	}
		
	public void setPreisGarantie(String PreisGarantie){
		switch(PreisGarantie) {
			case "3M": 
				minDurationInMonths =3;
				;break;
			case "6M":
				minDurationInMonths = 6;
				;break;
			case "9M":
				minDurationInMonths = 9;
			case "12M":
				minDurationInMonths = 12;
				;break;
			// ... enspricht egal
			default:	
				minDurationInMonths =0;
				break;
			}
	}
	
	@Override
	public String getUrl() {
		 return "" + this.postCode + "/" + "custom";
	 }
	
	public VeriVoxQueryCustom(String provider, String profile, String postcode, int annualTotal, int benchmarkTariffId ,int paolaLocationId, String paolaTypeLocationText) {
		// TODO Auto-generated constructor stub
		super(provider, profile, postcode,   annualTotal,  benchmarkTariffId , paolaLocationId, paolaTypeLocationText);
	}
	
	public VeriVoxQueryCustom(String provider,String profile, String postcode, int annualTotal, int benchmarkTariffId, String paolaTypeLocationText) {
		// TODO Auto-generated constructor stub
		super(provider, profile, postcode,  annualTotal,  benchmarkTariffId);
	}
}
