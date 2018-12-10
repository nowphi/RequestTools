package org.verivoxclient.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import org.verivoxclient.model.QueryModel;
import org.verivoxclient.model.VeriVoxQueryModel;
import org.verivoxclient.model.VeriVoxResponseModel;

public class VeriVoxOffersRequestThread extends VeriVoxThread  {

	VeriVoxQueryModel VM;

	// Eigener Parser für die Response
	VeriVoxResponseParser vp = new VeriVoxResponseParser();
	
	List<VeriVoxResponseModel>  result;
	
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
		requestResult =VeriVoxClient.requestOffers(VM);
		
		Predicate<String> pre = p -> p.equals(provider); 
		
		result = vp.parseResponse(requestResult, pre);
	}	
		
	@Override
	public String toString() {
		String delimiter = ";";
		
		// Type of QueryModel
		String queryType = VM.getClass().getAnnotation(QueryModel.class).name();
	
		// Results
		List <String> resultSet = new ArrayList<String>();
		
		for(VeriVoxResponseModel model : result) {	
			resultSet.add( String.join(delimiter,  date.toString(), queryType, VM.getPostcode(), VM.getPaolaTypeLocationText(), Integer.toString(VM.getAnnualTotal()), model.toString() ));	
		}
		
		return String.join("\n", resultSet);		
	}
}
