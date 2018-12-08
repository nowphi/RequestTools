package org.verivoxclient.frontend;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

public class BasicFront extends JFrame {

	/***
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	JTextArea resultarea;
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d");
	
	JComboBox<String> comboBoxProvider;
	JButton btnPostCodeList, btnSpeicherPfad, btnStart;
	JRadioButton rdbtnAll, rdbtnCustom , rdbtnRecommended;
	
	JLabel lbl_Speicherpfad, lbl_File, lblAusgabedateiAuswhlen;
		
	protected JTextField tf_verbrauch;
	protected final ButtonGroup buttonGroup = new ButtonGroup();
		
	public BasicFront() {
		// TODO Auto-generated constructor stub
					
			setTitle("VeriVoxRanking");
			getContentPane().setBackground(new Color(255, 204, 0));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(500, 200, 800, 590);
			GridBagLayout gridBagLayout = new GridBagLayout();
			gridBagLayout.columnWidths = new int[]{25, 81, 100, 0, 300, 0, 0, 25, 0};
			gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 19, 0, 0, 0, 0, 0, 28, 29, 0, 25, 0, 0, 0, 0, 0};
			gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
			gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			getContentPane().setLayout(gridBagLayout);
			
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
			gbc_scrollPane.gridwidth = 3;
			gbc_scrollPane.gridheight = 16;
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 4;
			gbc_scrollPane.gridy = 1;
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			getContentPane().add(scrollPane, gbc_scrollPane);
			
			JLabel Verbrauch = new JLabel("Verbrauch");
			GridBagConstraints gbc_Verbrauch = new GridBagConstraints();
			gbc_Verbrauch.anchor = GridBagConstraints.WEST;
			gbc_Verbrauch.insets = new Insets(0, 0, 5, 5);
			gbc_Verbrauch.gridx = 1;
			gbc_Verbrauch.gridy = 2;
			getContentPane().add(Verbrauch, gbc_Verbrauch);
			
			tf_verbrauch = new JTextField();
			tf_verbrauch.setText("2000");
			GridBagConstraints gbc_textField_1 = new GridBagConstraints();
			gbc_textField_1.insets = new Insets(0, 0, 5, 5);
			gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1.gridx = 2;
			gbc_textField_1.gridy = 2;
			getContentPane().add(tf_verbrauch, gbc_textField_1);
			tf_verbrauch.setColumns(10);
			
			JLabel lblProvider = new JLabel("Provider");
			GridBagConstraints gbc_lblProvider = new GridBagConstraints();
			gbc_lblProvider.insets = new Insets(0, 0, 5, 5);
			gbc_lblProvider.anchor = GridBagConstraints.WEST;
			gbc_lblProvider.gridx = 1;
			gbc_lblProvider.gridy = 3;
			getContentPane().add(lblProvider, gbc_lblProvider);
			
			String []provider = {"Strom.Manufaktur"};
			comboBoxProvider = new JComboBox<String>(provider);
			GridBagConstraints gbc_comboBoxProvider;
			gbc_comboBoxProvider = new GridBagConstraints();
			gbc_comboBoxProvider.insets = new Insets(0, 0, 5, 5);
			gbc_comboBoxProvider.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBoxProvider.gridx = 2;
			gbc_comboBoxProvider.gridy = 3;
			getContentPane().add(comboBoxProvider, gbc_comboBoxProvider);
			
			JLabel Vertragslaufzeit = new JLabel("Vertragslaufzeit");
			GridBagConstraints gbc_Vertragslaufzeit = new GridBagConstraints();
			gbc_Vertragslaufzeit.anchor = GridBagConstraints.WEST;
			gbc_Vertragslaufzeit.insets = new Insets(0, 0, 5, 5);
			gbc_Vertragslaufzeit.gridx = 1;
			gbc_Vertragslaufzeit.gridy = 9;
			getContentPane().add(Vertragslaufzeit, gbc_Vertragslaufzeit);
			
			String []vertragslaufzeit = {"12M","0", "3M", "6M" , "24M"};
			JComboBox<String> cb_vertragslaufzeit = new JComboBox<String>(vertragslaufzeit);
			cb_vertragslaufzeit.setEnabled(false);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 2;
			gbc_comboBox.gridy = 9;
			getContentPane().add(cb_vertragslaufzeit, gbc_comboBox);
			
			JLabel Preisgarantie = new JLabel("Preisgarantie");
			GridBagConstraints gbc_Preisgarantie = new GridBagConstraints();
			gbc_Preisgarantie.anchor = GridBagConstraints.WEST;
			gbc_Preisgarantie.insets = new Insets(0, 0, 5, 5);
			gbc_Preisgarantie.gridx = 1;
			gbc_Preisgarantie.gridy = 10;
			getContentPane().add(Preisgarantie, gbc_Preisgarantie);
			
			String []preisgarantie = {"12M", "0", "3M", "6M" ,"9M"};
			JComboBox<String> cb_preisgarantie = new JComboBox<String>(preisgarantie);
			cb_preisgarantie.setEnabled(false);
			GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
			gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_1.gridx = 2;
			gbc_comboBox_1.gridy = 10;
			getContentPane().add(cb_preisgarantie, gbc_comboBox_1);
					
			JLabel Kündigungsfrist = new JLabel("K\u00FCndigungsfrist");
			GridBagConstraints gbc_Kündigungsfrist = new GridBagConstraints();
			gbc_Kündigungsfrist.anchor = GridBagConstraints.WEST;
			gbc_Kündigungsfrist.insets = new Insets(0, 0, 5, 5);
			gbc_Kündigungsfrist.gridx = 1;
			gbc_Kündigungsfrist.gridy = 11;
			getContentPane().add(Kündigungsfrist, gbc_Kündigungsfrist);
			
			String []kunfrist = {"6W" ,"0", "1M", "2M", "3M"};
			JComboBox<String> cb_kunfrist = new JComboBox<String>(kunfrist);
			cb_kunfrist.setEnabled(false);
			GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
			gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_2.gridx = 2;
			gbc_comboBox_2.gridy = 11;
			getContentPane().add(cb_kunfrist, gbc_comboBox_2);
			
			JLabel Verlängerung = new JLabel("Verl\u00E4ngerung");
			GridBagConstraints gbc_Verlängerung = new GridBagConstraints();
			gbc_Verlängerung.anchor = GridBagConstraints.WEST;
			gbc_Verlängerung.insets = new Insets(0, 0, 5, 5);
			gbc_Verlängerung.gridx = 1;
			gbc_Verlängerung.gridy = 12;
			getContentPane().add(Verlängerung, gbc_Verlängerung);
			
			String []verl = { "12M", "0", "1M", "3M" ,"6M"};
			JComboBox<String> cb_verl = new JComboBox<String>(verl);
			cb_verl.setEnabled(false);
			GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
			gbc_comboBox_3.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_3.gridx = 2;
			gbc_comboBox_3.gridy = 12;
			getContentPane().add(cb_verl, gbc_comboBox_3);
			
			JLabel lblSorting = new JLabel("Sorting");
			GridBagConstraints gbc_lblSorting = new GridBagConstraints();
			gbc_lblSorting.anchor = GridBagConstraints.WEST;
			gbc_lblSorting.insets = new Insets(0, 0, 5, 5);
			gbc_lblSorting.gridx = 1;
			gbc_lblSorting.gridy = 13;
			getContentPane().add(lblSorting, gbc_lblSorting);
			
			String []sorting = {"Preis aufsteigend", "Preis absteigend", "Kundenbewertung absteigend"};
			JComboBox<String> cb_sorting = new JComboBox<String>(sorting);
			cb_sorting.setEnabled(false);
			GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
			gbc_comboBox_4.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox_4.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_4.gridx = 2;
			gbc_comboBox_4.gridy = 13;
			getContentPane().add(cb_sorting, gbc_comboBox_4);
							
			resultarea = new JTextArea();
			resultarea.setBackground(UIManager.getColor("Button.background"));
			resultarea.setEditable(false);
			
			resultarea.setCaretPosition(0);
			
			scrollPane.add(resultarea);
			scrollPane.setViewportView(resultarea);
			
			lbl_File = new JLabel("Datei ausw\u00E4hlen ...");
			GridBagConstraints gbc_lbl_File = new GridBagConstraints();
			gbc_lbl_File.anchor = GridBagConstraints.WEST;
			gbc_lbl_File.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_File.gridx = 2;
			gbc_lbl_File.gridy = 15;
			getContentPane().add(lbl_File, gbc_lbl_File);
			
			btnPostCodeList = new JButton("Postleitzahlen");	
			GridBagConstraints gbc_btnPostCodeList = new GridBagConstraints();
			gbc_btnPostCodeList.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnPostCodeList.insets = new Insets(0, 0, 5, 5);
			gbc_btnPostCodeList.gridx = 1;
			gbc_btnPostCodeList.gridy = 15;
			getContentPane().add(btnPostCodeList, gbc_btnPostCodeList);
					
			lbl_Speicherpfad = new JLabel("Speicherpfad ausw\u00E4hlen ...");
			GridBagConstraints gbc_lbl_Speicherpfad = new GridBagConstraints();
			gbc_lbl_Speicherpfad.anchor = GridBagConstraints.WEST;
			gbc_lbl_Speicherpfad.insets = new Insets(0, 0, 5, 5);
			gbc_lbl_Speicherpfad.gridx = 2;
			gbc_lbl_Speicherpfad.gridy = 16;
			getContentPane().add(lbl_Speicherpfad, gbc_lbl_Speicherpfad);
			
			btnSpeicherPfad = new JButton("Speicherpfad");
			GridBagConstraints gbc_btnSpeicherPfad = new GridBagConstraints();
			gbc_btnSpeicherPfad.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnSpeicherPfad.insets = new Insets(0, 0, 5, 5);
			gbc_btnSpeicherPfad.gridx = 1;
			gbc_btnSpeicherPfad.gridy = 16;
			getContentPane().add(btnSpeicherPfad, gbc_btnSpeicherPfad);
					
			rdbtnCustom = new JRadioButton("Eigenes Suchmuster");
			rdbtnCustom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(rdbtnCustom.isSelected() == true) {
						System.out.println("1");
						cb_vertragslaufzeit.setEnabled(true);
						cb_preisgarantie.setEnabled(true);
						cb_kunfrist.setEnabled(true);
						cb_verl.setEnabled(true);
						cb_sorting.setEnabled(true);
					}
				}
			});
		
			rdbtnRecommended = new JRadioButton("Verivox Empfehlungen");
			rdbtnRecommended.setSelected(true);
			rdbtnRecommended.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cb_vertragslaufzeit.setEnabled(false);
					cb_preisgarantie.setEnabled(false);
					cb_kunfrist.setEnabled(false);
					cb_verl.setEnabled(false);
					cb_sorting.setEnabled(false);
				}
			});
			rdbtnRecommended.setBackground(Color.ORANGE);
			buttonGroup.add(rdbtnRecommended);
			GridBagConstraints gbc_rdbtnRecommended = new GridBagConstraints();
			gbc_rdbtnRecommended.anchor = GridBagConstraints.WEST;
			gbc_rdbtnRecommended.gridwidth = 2;
			gbc_rdbtnRecommended.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnRecommended.gridx = 1;
			gbc_rdbtnRecommended.gridy = 5;
			getContentPane().add(rdbtnRecommended, gbc_rdbtnRecommended);
			
			rdbtnAll = new JRadioButton("Alle Tarife");
			rdbtnAll.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cb_vertragslaufzeit.setEnabled(false);
					cb_preisgarantie.setEnabled(false);
					cb_kunfrist.setEnabled(false);
					cb_verl.setEnabled(false);
					cb_sorting.setEnabled(false);
				}
			});
			rdbtnAll.setBackground(Color.ORANGE);
			buttonGroup.add(rdbtnAll);
			GridBagConstraints gbc_rdbtnAll = new GridBagConstraints();
			gbc_rdbtnAll.anchor = GridBagConstraints.WEST;
			gbc_rdbtnAll.gridwidth = 2;
			gbc_rdbtnAll.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnAll.gridx = 1;
			gbc_rdbtnAll.gridy = 6;
			getContentPane().add(rdbtnAll, gbc_rdbtnAll);
			
			rdbtnCustom.setBackground(Color.ORANGE);
			buttonGroup.add(rdbtnCustom);
			GridBagConstraints gbc_rdbtnCustom = new GridBagConstraints();
			gbc_rdbtnCustom.anchor = GridBagConstraints.WEST;
			gbc_rdbtnCustom.gridwidth = 2;
			gbc_rdbtnCustom.insets = new Insets(0, 0, 5, 5);
			gbc_rdbtnCustom.gridx = 1;
			gbc_rdbtnCustom.gridy = 7;
			getContentPane().add(rdbtnCustom, gbc_rdbtnCustom);
			
			lblAusgabedateiAuswhlen = new JLabel("Ausgabedatei ausw\u00E4hlen ...");
			GridBagConstraints gbc_lblAusgabedateiAuswhlen = new GridBagConstraints();
			gbc_lblAusgabedateiAuswhlen.anchor = GridBagConstraints.WEST;
			gbc_lblAusgabedateiAuswhlen.insets = new Insets(0, 0, 5, 5);
			gbc_lblAusgabedateiAuswhlen.gridx = 2;
			gbc_lblAusgabedateiAuswhlen.gridy = 17;
			getContentPane().add(lblAusgabedateiAuswhlen, gbc_lblAusgabedateiAuswhlen);
			
			btnStart = new JButton("Start");
			GridBagConstraints gbc_btnStart = new GridBagConstraints();
			gbc_btnStart.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnStart.insets = new Insets(0, 0, 5, 5);
			gbc_btnStart.gridx = 1;
			gbc_btnStart.gridy = 18;
			getContentPane().add(btnStart, gbc_btnStart);
			
			GridBagConstraints gbc_Abfrage = new GridBagConstraints();
			gbc_Abfrage.fill = GridBagConstraints.HORIZONTAL;
			gbc_Abfrage.gridwidth = 2;
			gbc_Abfrage.insets = new Insets(0, 0, 5, 5);
			gbc_Abfrage.gridx = 1;
			gbc_Abfrage.gridy = 10;
		}
}
