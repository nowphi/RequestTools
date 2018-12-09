package org.verivoxclient.dao;

import java.util.List;

import org.verivox.model.VeriVoxQueryModel;

public interface IHttpsVeriVoxClient {

	public List<String> requestPostCodeValue(String postcode);
	
	/**
	 * <b> public String requestBenchmarkTarifID(int annualTotal, String postcode) </b><br><br>
	 * 
	 * Sends a request to VeriVoxServer to get the ID of the BasicBenchmarkTariff (normally Grundversorgung). <br><br>
	 * 
	 * Sendet eine Anfrage an den VeriVoxServer, um die ID des BasisVergleichstarifs (in der Regel Grundversorgung) abzufragen.
	 * 
	 * @param annualTotal
	 * @param postcode
	 * @return BenchmarkTariffID
	 */
	public int requestBenchmarkTarifID(int annualTotal, String postcode, String paolaType);
	/**
	 * <b> public String requestBenchmarkTarifID(int annualTotal, String postcode, String tariff) </b><br><br>
	 * 	Sends a request to VeriVoxServer to get the ID of a chosen BenchmarkTariff. The tariff has to be available for des postcode/district. <br><br>
	 * 
	 * Sendet eine Anfrage an den VeriVoxServer, um die ID des gewählten Vergleichstarifs abzufragen.
	 * 
	 * 
	 * @param annualTotal
	 * @param postcode
	 * @param tariff
	 * @return BenchmarkTariffID
	 */
	public String requestBenchmarkTarifID(int annualTotal, String postcode, String paolaType,String tariff);
	/**
	 * <b> public String requestPostCodeValue(String postcode) </b><br><br>
	 * Sends a request to VeriVoxServer to get a list of the district value(s) of a postcode. <br><br>
	 * 
	 * Sendet eine Anfrage an den VeriVoxServer, um den Wert eines Ortsteils abzufragen. Gibt eine Liste mit den Werten oder -1 zurück.
	 * 
	 * <br>
	 * @param postcode
	 * @return A list of the VeriVoxPostCodeValues for the chosen postcode or -1 if there only one district.
	 */

	public String requestOffers(VeriVoxQueryModel model, String provider);
	
}
