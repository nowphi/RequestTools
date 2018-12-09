package org.verivoxclient.frontend;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.verivoxclient.api.VeriVoxOffersRequestThread;
import org.verivoxclient.controller.FileListController;
import org.verivoxclient.controller.VeriVoxRequestController;


public class FrontendVeriVox extends BasicFront {

	private static final long serialVersionUID = 1L;
	private FileListController listService;
	private VeriVoxRequestController requestController;
	private List<String> PostCodeList;
	
	String PathPostCodeList, SavePath, filename, provider;
	
	/**
	 * Launch the application.
	 */

	
	public FrontendVeriVox() {
		super();		
		
		// FileServiceController
		listService = new FileListController();
		
		
		
			btnPostCodeList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					int returnValue = jfc.showOpenDialog(null);

					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jfc.getSelectedFile();
						PathPostCodeList = selectedFile.getAbsolutePath();
						
						if(selectedFile.length() > 700) {
							resultarea.append("PostleitzahlenListe zu groß (zuviele Postleitzahlen). Nur ca. 100 aufeinmal erlaubt!\n");
							PathPostCodeList = null;
							return;
						}
						
						
							
						lbl_File.setText(PathPostCodeList);
					}
				}
			});
			
			btnSpeicherPfad.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
					int returnValue = jfc.showDialog(null, "Dateipfad auswählen");

					if (returnValue == JFileChooser.APPROVE_OPTION) {
						File selectedFile = jfc.getSelectedFile();
						SavePath = selectedFile.getAbsolutePath();
						lbl_Speicherpfad.setText(SavePath);
						filename = simpleDateFormat.format(new Date()) + "_VeriVox_";
						lblAusgabedateiAuswhlen.setText( filename );
					}
				}
			});
			
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
			
					int annualTotal = 0;
					try {
					annualTotal = Integer.parseInt(tf_verbrauch.getText());
					} catch ( NumberFormatException e) {
						resultarea.append("Kein gültiges Zahlenformat bei Verbrauch!\n");
					}
					
					int c = -1;
					
					if(	rdbtnCustom.isSelected()) { 
						c = 0; 
						resultarea.append("Individuelle Abfragen gehen momentan noch nicht!\n");
						return;
					}
					if(	rdbtnAll.isSelected())  c = 2; 
					if(	rdbtnRecommended.isSelected() ) c = 1; 
					
					provider = comboBoxProvider.getSelectedItem().toString();
										
					if(PathPostCodeList != null) {
						System.out.println(PathPostCodeList);
					} else {
						resultarea.append("Keine PostleitzahlenListe ausgewählt!\n");
						return;
					}
					
					if(SavePath != null) {
						System.out.println(PathPostCodeList);
					} else {
						resultarea.append("Keine Speicherpfad ausgewählt!\n");
						return;
					}
							
					PostCodeList = listService.loadPostCodeListFromFile(PathPostCodeList); 
					requestController = new VeriVoxRequestController(PostCodeList);
						
					// LocationID
					requestController.createListLocationProperties(annualTotal);
					resultarea.append("Get Locations. Size: " + requestController.getSizePostCodeLocationList() + "\n"); 
					
					if(c != 0) {
					// 0 custom // 1 recommended // 2 all
					requestController.requestPostCodeList(c, provider);	
					}
															
					String save = SavePath + "/" + filename + annualTotal + "_" + c + ".txt";
					
					List<VeriVoxOffersRequestThread> list = requestController.getVeriVoxOffersRequestThread();
					
					if(listService.saveToFile(list , save) == 0 && list.isEmpty() == false) {
						resultarea.append("Datei gespeichert unter:\n" + save + "\n");
					} else {
						resultarea.append("Liste konnte nicht abgefragt oder gespeichert werden.\n Bitte überprüfen Sie die Eingabeparameter.\n");
					}
				}
			});	
	}
}
