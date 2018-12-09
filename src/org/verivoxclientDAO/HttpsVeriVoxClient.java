package org.verivoxclientDAO;
import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;

import org.verivox.model.VeriVoxQueryModel;

import json.JSONArray;
import json.JSONObject;
import json.JSONTokener;

public class HttpsVeriVoxClient implements IHttpsVeriVoxClient {
	// Eigener Parser für die Response
	VeriVoxParser vp = new VeriVoxParser();
	
	// Default-URLs
	private String LOCATION_PROPERTIES_DEFAULT = "https://www.verivox.de/verivoxenergyjsonservices.ashx/getlocationfrompostcode?paolaType=1&postCode=";
	private String LOCATION_BenchmarkTarifID_DEFAULT = "https://www.verivox.de/servicehook/benchmarks/electricity/profiles/H0/locations/";
	
	// Definition des Request-Headers: JSON akzeptiert, Kompression gzip
	private final String accept = "application/json";	
	private final String contenttyp = "application/json"; 
	private final String acceptencoding = "gzip, deflate, br";	
	
	/**
	 *  Material-Properties <br>
	 	Material entspricht TimeStamp in Sekunden (reverse) <br>
	    07.09.2018,22:00:00 -> 002966985 (reverse, Inkrement die erste Ziffer), also richtig 589669200
	 */
	Supplier<String> supMaterial = () -> calculateMaterial();
			
	/**
	 * Wert in Sekunden des Verivox-Timestamps
	 */
	private final long verivox_time_old = 589669200;
	
	private String calculateMaterial(){
		
		// Repräsentativer Java-TimeStamp mit Milisec-Wert des Startdatums
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d hh:mm:ss");
		Date date = null;
		try {
			date = simpleDateFormat.parse("2018-09-07 22:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Timestamp timestamp_start  = new Timestamp(date.getTime());
		long value_timestamp_start = timestamp_start.getTime();
		
		// Aktuelles Datum (Ende)
		Calendar calendar = Calendar.getInstance();
		// Repräsentativer Java-TimeStamp mit Milisec-Wert des Enddatums
		Timestamp timestamp_end = new Timestamp(calendar.getTime().getTime());
		long value_timestamp_end = timestamp_end.getTime();
		
		// Berechnung vergangener Sekunden Start<->Ende
		long time_elapsed = (value_timestamp_end - value_timestamp_start) / 1000;
		long verivox_current = verivox_time_old + time_elapsed;
		
		return (new StringBuilder( Long.toString(verivox_current) ).reverse().toString());
	}
	
	
	@Override
	public List<String> requestPostCodeValue(String postcode) {
	
		String url_str =(LOCATION_PROPERTIES_DEFAULT + postcode);
		URL url;
		
		List<String> list = new ArrayList<String>();
		BufferedReader input;
		
		try {
			url = new URL( url_str );
		
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
				
			input = new BufferedReader(new InputStreamReader(connection.getInputStream(),  "UTF-8"));
	
			JSONObject jo = new JSONObject(new JSONTokener(input));
			input.close();
			connection.disconnect();
		
			jo.getJSONArray("result").forEach(s -> list.add(s.toString()));
	
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		return list;
	}

	@Override
	public String requestBenchmarkTarifID(int annualTotal, String postcode, String paolaType ,String tariff) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int requestBenchmarkTarifID(int annualTotal, String postcode, String paolaType) {
		// TODO Auto-generated method stub
		URL urlObj;
		BufferedReader input;
		
		String benchmarkTarifID = null, urlStr;
		
		if(paolaType.equals("0") == false) 
			urlStr = LOCATION_BenchmarkTarifID_DEFAULT + postcode + "/" +paolaType +"/?usage=" + annualTotal;
		else 
			urlStr = LOCATION_BenchmarkTarifID_DEFAULT + postcode + "/?usage=" + annualTotal;
		
		try {
			urlObj = new URL( urlStr );
		
			HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
			connection.setRequestProperty( "Accept",  accept);	
			connection.setRequestProperty( "Content-Type",  contenttyp);
			connection.setRequestProperty( "Accept-Encoding",  acceptencoding);	
		
			input = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream()),  "UTF-8"));
	
			JSONObject jo = new JSONObject(new JSONTokener(input));
			input.close();
			JSONArray ja = jo.getJSONArray("providers");
			
			benchmarkTarifID =  (String) ja.getJSONObject(0).getJSONArray("products").getJSONObject(0).get("id");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.parseInt(benchmarkTarifID);
	}

	public HttpsURLConnection setConnectionRequestProperty(HttpsURLConnection connection ) {
		connection.setRequestProperty( "Accept",  accept);	
		connection.setRequestProperty( "Content-Type",  contenttyp);
		connection.setRequestProperty( "Accept-Encoding",  acceptencoding);	
		connection.setRequestProperty( "Material",  supMaterial.get());
		
		return connection;
	}
	
	@Override
	public String requestOffers(VeriVoxQueryModel model, String provider) {
		// TODO Auto-generated method stub
		String result = null;
		
		// OutputStream in Byte
		OutputStream writer;

		try {
			// URL genergieren
			URL url = new URL(model.getUrl());
					
			byte [] JSONQuery = model.toString().getBytes("UTF-8");
			// HttpsURLConnection mit Post bauen, Output true
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			
			// Request-Header, Material ist ein VeriVoxTimeStampt, der mit getMaterial() erzeugt wird
			connection = this.setConnectionRequestProperty(connection);	
			connection.connect();
		
			writer = connection.getOutputStream( );
			
			writer.write(JSONQuery);
			writer.flush();
			writer.close();
			
			// Rückgabe ist "303 See Other", als Location wird ein Verweis auf eine URL mit GET zurückgegeben (mit Zusatz zur ursprünglich angefragten)
			if( HttpURLConnection.HTTP_UNAUTHORIZED == connection.getResponseCode() )
				
				// es muss die neue URL für eine neue HttosURLConnection verwendet werden (GET)
				connection.disconnect();
				URL location = connection.getURL();
				connection = (HttpsURLConnection) location.openConnection();
				connection.setRequestMethod("GET");
				connection = this.setConnectionRequestProperty(connection);	
				
				connection.connect();
		
				BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream()),  "UTF-8"));
				Predicate<String> pre = p -> p.equals(provider); 
				result = vp.parseResultPredicate(in, pre );
				in.close();
					
				connection.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return result;
	}
}
