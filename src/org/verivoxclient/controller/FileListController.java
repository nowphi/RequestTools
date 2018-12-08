package org.verivoxclient.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.verivoxclient.api.VeriVoxOffersRequestThread;

public class FileListController {
	
	public List<String> loadPostCodeListFromFile(String filename) {
		
		List<String> PostCodeList = new ArrayList<String>();
		
		File f = new File(filename);
		FileReader fr = null;
		try {
			fr = new FileReader(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader in = new BufferedReader(fr);
		try {
			String line;
			while(( line = in.readLine()) != null) {
				PostCodeList.add(line.split("\t")[0]);
				}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PostCodeList;
	}
	
	public int saveToFile(List<VeriVoxOffersRequestThread> list, String filename) {
		
		File f = new File(filename);
		FileWriter fr = null;

			try {
				fr = new FileWriter(f);
				BufferedWriter out = new BufferedWriter(fr);
				
				out.write("Abgerufen am;TarifSchnellAuswahl;Postleitzahl;Ortsteil;Verbrauch;Treffer;AnbieterRank;AnbieterName;Gesamtkosten;VergleichsAnbieter;VergleichsTariff;VergleichsKosten");
				out.newLine();
				
				list.
					stream().
					forEach(s -> {
						try {
							out.write(s.toString());
							out.newLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				out.flush();
				out.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return 0;
	}
}
