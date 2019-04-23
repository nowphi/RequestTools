package org.verivoxclient.controller;
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
import java.util.function.Supplier;
import javax.net.ssl.HttpsURLConnection;

import org.brotli.dec.BrotliInputStream;
import org.verivoxclient.model.VeriVoxQueryModel;

import json.JSONArray;
import json.JSONObject;
import json.JSONTokener;

public class HttpsVeriVoxClient implements IHttpsVeriVoxClient {

	// Default-URLs
	//private String LOCATION_PROPERTIES_DEFAULT = "https://www.verivox.de/verivoxenergyjsonservices.ashx/getlocationfrompostcode?paolaType=1&postCode=";
	//private String LOCATION_BenchmarkTarifID_DEFAULT = "https://www.verivox.de/servicehook/benchmarks/electricity/profiles/H0/locations/";
//	String urlStr = "https://www.verivox.de/servicehook/locations/electricity/postCode/"+postCode;
	
	// *** https://www.verivox.de/ ohne DNS direkt IP 104.17.83.237:443 ***
	String protocol = "https";
	String host = "www.verivox.de";
	int port = 443;
			
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
	public List<String> requestPostCodeValue(String postCode) {
	
		String file = "/servicehook/locations/electricity/postCode/" + postCode;
		URL url;
		
		List<String> list = new ArrayList<String>();
		BufferedReader input;
		
		try {
			url = new URL( protocol,  host,  port,  file);

			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
	
			// Diese Parameter müssen bei abweichenden als Code 2** angepasst werden
			connection.setRequestMethod("GET");
			connection.setRequestProperty( "Accept",  "application/json, text/plain, application/vnd.verivox.energyLocation-v3+json");	
			connection.setRequestProperty( "Accept-Encoding",  acceptencoding);	
			connection.setRequestProperty( "user-agent",  "*");	

			input = new BufferedReader(new InputStreamReader(connection.getInputStream(),  "UTF-8"));
			
			JSONObject jo = new JSONObject(new JSONTokener(input));
			jo.getJSONArray("locationList").forEach(s -> list.add(s.toString()));
		
			input.close();
			
			connection.disconnect();

			} catch (Exception e) {
			// TODO Auto-generated catch block
				System.out.println(e);
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
		
		String benchmarkTarifID = null,  file;
		
		if(paolaType.equals("0") == false) {
			file = "/servicehook/benchmarks/electricity/profiles/H0/locations/" + postcode + "/" + paolaType +"/?usage=" + annualTotal;
		}else {
			file = "/servicehook/benchmarks/electricity/profiles/H0/locations/" + postcode + "/?usage=" + annualTotal;
		}
		
		try {
			urlObj = new URL( protocol,  host,  port,  file);
			//System.out.println(urlObj.toURI());
		
			HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
			connection.setRequestProperty( "Accept",  accept);	
			connection.setRequestProperty( "Content-Type",  contenttyp);
			connection.setRequestProperty( "Accept-Encoding",  acceptencoding);	
			connection.setRequestProperty( "user-agent",  "*");	
	
			// 20.04.2019 von gzip auf br 
			input = new BufferedReader(new InputStreamReader(new BrotliInputStream(connection.getInputStream()),  "UTF-8"));
		
			JSONObject jo = new JSONObject(new JSONTokener(input));
	
			input.close();
			JSONArray ja = jo.getJSONArray("providers");
			
			benchmarkTarifID =  (String) ja.getJSONObject(0).getJSONArray("products").getJSONObject(0).get("id");
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return Integer.parseInt(benchmarkTarifID);
	}

	private HttpsURLConnection setConnectionRequestProperty(HttpsURLConnection connection ) {
		connection.setRequestProperty( "Accept",  accept);	
		connection.setRequestProperty( "Content-Type",  contenttyp);
		connection.setRequestProperty( "Accept-Encoding",  acceptencoding);	
		connection.setRequestProperty( "user-agent",  "*");	
		connection.setRequestProperty( "Material",  supMaterial.get());
		
		return connection;
	}
	
	@Override
	public String requestOffers(VeriVoxQueryModel model) {
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
		
				BufferedReader in = new BufferedReader(new InputStreamReader(new BrotliInputStream(connection.getInputStream()),  "UTF-8"));
			//	BufferedReader in = new BufferedReader(new InputStreamReader(new GZIPInputStream(connection.getInputStream()),  "UTF-8"));
				result = in.readLine();
				in.close();
					
				connection.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
		}
		return result;
	}
}
