package com;

import javax.swing.JFrame;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GUI {

	private JFrame frmProxyScraper;
	private JComboBox<String> comboBox;
	private JTextArea textAreaProxies;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	public JFrame getFrame() {
		return frmProxyScraper;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// JFrame
		frmProxyScraper = new JFrame();
		frmProxyScraper.getContentPane().setBackground(Color.DARK_GRAY);
		frmProxyScraper.setTitle("Public Proxy Scraper");
		frmProxyScraper.setBounds(100, 100, 360, 271);
		frmProxyScraper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProxyScraper.getContentPane().setLayout(null);
		frmProxyScraper.setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/com/images/ico.png")));
		frmProxyScraper.setLocationRelativeTo(null);
		
		// clear button
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaProxies.setText("");
			}
		});
		btnClear.setFocusable(false);
		btnClear.setBounds(192, 205, 120, 22);
		frmProxyScraper.getContentPane().add(btnClear);
		
		// Scrape button
		JButton btnScrape = new JButton("Scrape proxies");
		btnScrape.setFocusable(false);
		btnScrape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Thread psThread = new Thread() {
					@Override
					public void run() {
						lblStatus.setText("Establishing connection");
						btnScrape.setEnabled(false);
						btnClear.setEnabled(false);
						Proxy ps = new Proxy();
						String proxies = ps.getProxies(comboBox.getSelectedIndex());
						textAreaProxies.setText(proxies);
						lblStatus.setText("Finished with " + ps.getProxyCount() + " proxies");
						btnScrape.setEnabled(true);
						btnClear.setEnabled(true);
					}
				};
				psThread.start();
			}
		});
		btnScrape.setBounds(40, 205, 120, 22);
		frmProxyScraper.getContentPane().add(btnScrape);

		// Combox
		comboBox = new JComboBox<String>();
		comboBox.setFocusable(false);
		comboBox.setModel(
				new DefaultComboBoxModel<String>(new String[] {"ALL + remove duplicates", "USHTTP", "SSL", "NOVA", "GMP", "ID (takes some time)"}));
		comboBox.setBounds(40, 28, 150, 22);
		frmProxyScraper.getContentPane().add(comboBox);

		// textAreaProxies
		textAreaProxies = new JTextArea();
		textAreaProxies.setBackground(Color.LIGHT_GRAY);
		textAreaProxies.setBounds(40, 61, 272, 133);
		frmProxyScraper.getContentPane().add(textAreaProxies);
		
		JScrollPane scrollPane = new JScrollPane(textAreaProxies);
		scrollPane.setBounds(40, 61, 272, 133);
		frmProxyScraper.getContentPane().add(scrollPane);
		
		lblStatus = new JLabel("");
		lblStatus.setForeground(Color.WHITE);
		lblStatus.setBackground(Color.WHITE);
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(200, 28, 134, 20);
		frmProxyScraper.getContentPane().add(lblStatus);
	}
}
