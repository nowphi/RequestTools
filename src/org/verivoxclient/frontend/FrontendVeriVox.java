package org.verivoxclient.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.verivoxclient.controller.FileListController;
import org.verivoxclient.controller.VeriVoxRequestController;
import org.verivoxclient.dao.VeriVoxOffersRequestThread;


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
						
						if(selectedFile.length() > 500) {
							resultarea.append("PostleitzahlenListe zu groß (zuviele Postleitzahlen). Nur ca. 50 aufeinmal erlaubt!\n");
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
						resultarea.append("PostCodeListe gewählt " + PathPostCodeList + "\n"); 
					} else {
						resultarea.append("Keine PostleitzahlenListe ausgewählt!\n");
						return;
					}
					
					if(SavePath != null) {
						resultarea.append("Speicherpfad gesetzt " + SavePath + "\n"); 
						System.out.println();
					} else {
						resultarea.append("Keine Speicherpfad ausgewählt!\n");
						return;
					}
							
					PostCodeList = listService.loadPostCodeListFromFile(PathPostCodeList); 
					requestController = new VeriVoxRequestController(PostCodeList);
					
					// LocationID
					requestController.createListLocationProperties(annualTotal);
					resultarea.append("Get Locations. Size: " + requestController.getSizePostCodeLocationList() + "\n"); 
					resultarea.update(resultarea.getGraphics());
					
					String save = SavePath + "/" + filename + annualTotal + "_" + c + ".txt";
					
					resultarea.append("Starte Abfrage. Dieser Vorgang kann einen Moment dauern ..." + "\n");								
					resultarea.update(resultarea.getGraphics());
					
					try {
						Thread.sleep( 2000 );
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(c != 0) {
					// 0 custom // 1 recommended // 2 all
					resultarea.append("Initialisiere Abfragemuster ..." + "\n"); 
					requestController.requestPostCodeList(c, provider);	
					}
					
					List<VeriVoxOffersRequestThread> list = requestController.getVeriVoxOffersRequestThread();
					
					if(listService.saveToFile(list , save) == 0 && list.isEmpty() == false) {
						resultarea.append("Datei gespeichert unter:\n" + save + "\n");
						resultarea.append("------------------------------------------\n");
					} else {
						resultarea.append("Liste konnte nicht abgefragt oder gespeichert werden.\n Bitte überprüfen Sie die Eingabeparameter.\n");
					}
				}
			});	
	}
}
