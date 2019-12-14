package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.apache.commons.lang3.StringUtils;

public class Proxy {
	
	public static Double VERSION = 0.1;
	private String strHtml;
	private String strProxies;
	private URLConnection spoof;
	private URL url;
	private String ip = "";
	private String port = "";
	
	public static void main(String[] args) {
		GUI window = new GUI();
		window.getFrame().setVisible(true);
	}

	public String getProxies(int index) {
		switch(index) {
		case 0:
			return getAll();
		case 1:
			return getUSHTTP();
		case 2:
			return getSSL();
		case 3:
			return getNova();
		case 4:
			return getGMP();
		case 5:
			return getID();
		default:
			return "";
		}
	}

	private String getID() {
		return null;
	}

	private String getNova() {
		return null;
	}

	private String getGMP() {
		try {
			url = new URL("http://givemeproxy.com/");
			spoof = url.openConnection();
			spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
			BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
			String[] ips = getSource(in, url, "ip");
			strProxies = "";
			ip = "";
			if(ips != null)
				for(int i = 0; i < ips.length; i++) { //gets the ips
						ip += ips[i] + "\n";
			}
			String[] ports = getSource(in, url, "port");
			if(ports != null)
				for(int i = 0; i < ports.length; i++) { //gets the ports
						port += ports[i] + "\n";
			}
		} catch (IOException e) {
		}
		return strProxies + ip + port; //remove null first line and combine both first lines of strings
	}

	private String getAll() {
		String all = "";
		String ushttp = getUSHTTP();
		String ssl = getSSL();
		all = ushttp + ssl;
		return all;
	}

	private String[] getSource(BufferedReader in, URL url, String pattern) throws IOException {
		String[] tds;
		while ((strProxies = in.readLine()) != null) {
			strHtml += strProxies;
		}
		URL test = new URL("http://givemeproxy.com/");
		if(url.sameFile(test) && pattern == "ip") 
			tds = StringUtils.substringsBetween(strHtml, "<td class=\"column-1\">", "</td>");
		else if(url.sameFile(test) && pattern == "port") 
			tds = StringUtils.substringsBetween(strHtml, "<td class=\"column-2\">", "</td>");
		else
			tds = StringUtils.substringsBetween(strHtml, "<td>", "</td>");
		return tds;
	}
	
	public String getUSHTTP() {
		try {
			url = new URL("https://www.us-proxy.org/");
			spoof = url.openConnection();
			spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
			BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
			String[] tds = getSource(in, url, "");
			strProxies = "";
			if(tds != null)
				for(int i = 0; i < tds.length; i++) {
					if(!tds[i].contains("-") && !tds[i].contains(":")) //try and get only IP and port
						if(tds[i].contains(".")) 
							if(tds[i + 1].matches("[0-9]{1,5}$")) //make sure the next element is a port
								strProxies += tds[i] + ":" + tds[i + 1] + "\n";
			}
		} catch (IOException e) {
		}
		return strProxies;
	}

	
	public String getSSL() {
		try {
			url = new URL("https://www.sslproxies.org/");
			spoof = url.openConnection();
			spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
			BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
			String[] tds = getSource(in, url, "");
			strProxies = "";
			if(tds != null)
				for(int i = 0; i < tds.length; i++) {
					if(!tds[i].contains("-") && !tds[i].contains(":")) //try and get only IP and port
						if(tds[i].contains(".")) 
							if(tds[i + 1].matches("[0-9]{1,5}$")) //make sure the next element is a port
									strProxies += tds[i] + ":" + tds[i + 1] + "\n";
			}
		} catch (IOException e) {
		}
		return strProxies;
	}
	
}
