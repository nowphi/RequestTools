package org.verivoxclient.dao;

import java.util.Date;

import org.verivoxclient.model.QueryModel;
import org.verivoxclient.model.VeriVoxQueryModel;

public class VeriVoxOffersRequestThread extends VeriVoxThread  {

	VeriVoxQueryModel VM;
	String requestResult;
	String provider;
	Date date = new Date();
	
	public VeriVoxOffersRequestThread(VeriVoxQueryModel model, String provider ) {
		super();
		this.VM = model;
		this.provider = provider;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		VM.toJSONQuery();
		requestResult =VeriVoxClient.requestOffers(VM, provider);
	}	
	
	@Override
	public String toString() {
		String delimiter = ";";
		
		// Type of QueryModel
		String queryType = VM.getClass().getAnnotation(QueryModel.class).name();
		
		return String.join(delimiter, date.toString(), queryType, VM.getPostcode(), VM.getPaolaTypeLocationText(), Integer.toString(VM.getAnnualTotal()), requestResult);
	}
}
