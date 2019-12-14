package com;

import javax.swing.JFrame;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class GUI {

	private JFrame frmProxyScraper;
	private JComboBox<String> comboBox;
	private JTextArea textAreaProxies;

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
		frmProxyScraper.setTitle("Proxy Scraper v" + Proxy.VERSION);
		frmProxyScraper.setBounds(100, 100, 360, 271);
		frmProxyScraper.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProxyScraper.getContentPane().setLayout(null);

		// Scrape button
		JButton btnScrape = new JButton("Scrape proxies");
		btnScrape.setFocusable(false);
		btnScrape.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Proxy ps = new Proxy();
				String proxies = ps.getProxies(comboBox.getSelectedIndex());
				textAreaProxies.setText(proxies);
			}
		});
		btnScrape.setBounds(40, 205, 120, 22);
		frmProxyScraper.getContentPane().add(btnScrape);

		// Combox
		comboBox = new JComboBox<String>();
		comboBox.setFocusable(false);
		comboBox.setModel(
				new DefaultComboBoxModel<String>(new String[] { "ALL", "USHTTP", "SSL", "NOVA", "GMP", "ID" }));
		comboBox.setBounds(40, 28, 100, 22);
		frmProxyScraper.getContentPane().add(comboBox);

		// textAreaProxies
		textAreaProxies = new JTextArea();
		textAreaProxies.setBackground(Color.LIGHT_GRAY);
		textAreaProxies.setBounds(40, 61, 272, 133);
		frmProxyScraper.getContentPane().add(textAreaProxies);

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
		
		JScrollPane scrollPane = new JScrollPane(textAreaProxies);
		scrollPane.setBounds(40, 61, 272, 133);
		frmProxyScraper.getContentPane().add(scrollPane);
	}
}
