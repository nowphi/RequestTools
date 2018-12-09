package org.verivoxclient.dao;

import java.io.BufferedReader;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Vorläufiger Parser mit Schwächen. Kein Parsing des JSON-Formats, sondern Tokenizing mit festen Sprung an die entsprechende Stellen.
 * @author user
 *
 */
public class VeriVoxParser {
	
	int rank = 0, max_rank = 0;
	
	public String parseResultPredicate(BufferedReader in, Predicate<String> pre) {
		String answer = "";
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(in).useDelimiter("\\{");
		String cost = null, searchedProvider = null;
		boolean schalter = false;
		
		while(sc.hasNext()) {
			String str = sc.next();
			String cost_bench = new String(), basicprovider_tarif  = new String(), basicprovider_name = new String(), rank =new String(), provider = null;
			String [] str_arr_rank, str_arr_cost;
			
			if(str.contains("\"benchmarkTariff\"") == true) {
				cost_bench = sc.next().split("\"")[19];// str_arr[3];
				
				sc.next();sc.next();
				basicprovider_tarif = sc.next().split("\"")[3];
				
				sc.next();sc.next();
				basicprovider_name = sc.next().split("\"")[3];
				
				//Strom.Manufaktur
				if(answer.matches(".*Strom.Manufaktur.*") == false) {
					answer = answer.concat(";"+";"+";");
				}
				
				answer = answer.concat(basicprovider_name +";");//"VergleichsProvider:" +
				answer = answer.concat(basicprovider_tarif +";");//"VergleichsTariff:" +
				answer = answer.concat(cost_bench);//"VergleichsCost:" + 
			}
				
			if(str.substring(0, 6).compareTo("\"rank\"") == 0) {
				rank = str.split("\"")[3];
				sc.next(); sc.next();
				str_arr_rank = sc.next().split("\"");
				provider = new String(str_arr_rank[3]);	
				
				if( (Integer.parseInt(rank)) > 0) {
					max_rank++;
				}
				
			}	
			
			if(str.compareTo("\"totalCost\":") == 0) {
				str_arr_cost = sc.next().split("\"");
				cost = new String(str_arr_cost[3]);
				
				if(searchedProvider != null) {
					if(schalter == true) {
						answer = answer.concat(cost + ";");//"Gesamtkosten:"+ 
						schalter = false;
					}
				}
			}	
			
			if(provider != null) {
				if(pre.test(provider)) {
					if(Integer.parseInt(rank) > 0) {
							answer = answer.concat(rank + ";" +  provider + ";");//"Rank:"+ //"Providername:" +
							searchedProvider = provider;
							schalter = true;
					}
				}
			}
		}
		return  max_rank + ";" +answer;
	}
	
}
