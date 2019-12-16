package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Proxy {

	public static Double VERSION = 0.1;
	private String strHtml;
	private URLConnection spoof;
	private URL url;
	private String ipPattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
	private int proxyCount = 0;

	public static void main(String[] args) {
		GUI window = new GUI();
		window.getFrame().setVisible(true);
	}

	public String getProxies(int index) {
		switch (index) {
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

	private String removeDuplicates(String ids, String ushttp, String gmp, String ssl, String nova) {
		String ips = ids + ushttp + gmp + ssl + nova;
		proxyCount = 1;
		String[] tokens = ips.split("\n");
		StringBuilder resultBuilder = new StringBuilder();
		Set<String> alreadyPresent = new HashSet<String>();
		boolean first = true;
		for (String token : tokens) {
			if (!alreadyPresent.contains(token)) {
				if (first)
					first = false;
				else {
					resultBuilder.append("\n");
					proxyCount++;
				}
				if (!alreadyPresent.contains(token))
					resultBuilder.append(token);
			}
			alreadyPresent.add(token);
		}
		String removed = resultBuilder.toString();
		return removed;
	}

	private String getID() {
		String strProxiesID = "";
		try {
			url = new URL("http://www.idcloak.com/proxylist/free-us-proxy-list.html");
			spoof = url.openConnection();
			spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
			BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
			String[] tds = getSource(in, url, "");
			for (int i = 0; i < tds.length - 1; i++) {
				if (tds != null)
					if (tds[i + 1].matches(ipPattern)) { // check if next element in array is an ip address
						strProxiesID += tds[i + 1] + ":";
						strProxiesID += tds[i] + "\n";
						proxyCount++;
					}
			}
		} catch (IOException e) {
		}
		return strProxiesID;
	}

	private String getNova() {
		String strProxiesNOVA = "";
		try {
			url = new URL("https://www.proxynova.com/proxy-server-list/port-8080/");
			spoof = url.openConnection();
			spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
			BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
			String[] ips = getSource(in, url, "ip");
			String[] ports = getSource(in, url, "port");

			if (ips != null && ports != null)
				for (int i = 0; i < ips.length; i++) {
					if (ips[i].matches(ipPattern)) {
						strProxiesNOVA += ips[i] + ":" + ports[i] + "\n";
						proxyCount++;
					}
				}
		} catch (IOException e) {
		}
		return strProxiesNOVA;
	}

	private String getGMP() {
		String strProxiesGMP = "";
		try {
			url = new URL("http://givemeproxy.com/");
			spoof = url.openConnection();
			spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
			BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
			String[] ips = getSource(in, url, "ip");
			String[] ports = getSource(in, url, "port");

			if (ips != null && ports != null)
				for (int i = 0; i < ips.length; i++) {
					strProxiesGMP += ips[i] + ":" + ports[i] + "\n";
					proxyCount++;
				}
		} catch (IOException e) {
		}
		return strProxiesGMP;
	}

	private String getAll() {
		String ids = getID();
		String ushttp = getUSHTTP();
		String gmp = getGMP();
		String ssl = getSSL();
		String nova = getNova();
		String all = removeDuplicates(ids, ushttp, gmp, ssl, nova);
		return all;
	}

	private String[] getSource(BufferedReader in, URL url, String ipPort) throws IOException {
		String strProxies = "";
		String[] tds;
		while ((strProxies = in.readLine()) != null) {
			strHtml += strProxies;
		}
		if (url.toString().equals("http://givemeproxy.com/") && ipPort.equals("ip"))
			tds = StringUtils.substringsBetween(strHtml, "<td class=\"column-1\">", "</td>");
		else if (url.toString().equals("http://givemeproxy.com/") && ipPort.equals("port"))
			tds = StringUtils.substringsBetween(strHtml, "<td class=\"column-2\">", "</td>");
		else if (url.toString().equals("https://www.proxynova.com/proxy-server-list/port-8080/") && ipPort.equals("ip"))
			tds = StringUtils.substringsBetween(strHtml, "<abbr title=\"", "\">");
		else if (url.toString().equals("https://www.proxynova.com/proxy-server-list/port-8080/")
				&& ipPort.equals("port"))
			tds = StringUtils.substringsBetween(strHtml,
					"<a href=\"/proxy-server-list/port-8080/\" title=\"Port 8080 proxies\">", "</a>");
		else
			tds = StringUtils.substringsBetween(strHtml, "<td>", "</td>");
		return tds;
	}

	private String getUSHTTP() {
		String strProxiesHTTP = "";
		try {
			url = new URL("https://www.us-proxy.org/");
			spoof = url.openConnection();
			spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
			BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
			String[] tds = getSource(in, url, "");
			strProxiesHTTP = "";
			if (tds != null)
				for (int i = 0; i < tds.length; i++) {
					if (!tds[i].contains("-") && !tds[i].contains(":")) // try and get only IP and port
						if (tds[i].contains("."))
							if (tds[i + 1].matches("[0-9]{1,5}$"))  { // make sure the next element is a port
								strProxiesHTTP += tds[i] + ":" + tds[i + 1] + "\n";
								proxyCount++;
							}
				}
		} catch (IOException e) {
		}
		return strProxiesHTTP;
	}

	private String getSSL() {
		String strProxiesSSL = "";
		try {
			url = new URL("https://www.sslproxies.org/");
			spoof = url.openConnection();
			spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
			BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()));
			String[] tds = getSource(in, url, "");
			strProxiesSSL = "";
			if (tds != null)
				for (int i = 0; i < tds.length; i++) {
					if (!tds[i].contains("-") && !tds[i].contains(":")) // try and get only IP and port
						if (tds[i].contains("."))
							if (tds[i + 1].matches("[0-9]{1,5}$")) { // make sure the next element is a port
								strProxiesSSL += tds[i] + ":" + tds[i + 1] + "\n"; 
								proxyCount++;
							}
				}
		} catch (IOException e) {
		}
		return strProxiesSSL;
	}

	public int getProxyCount() {
		return proxyCount;
	}

	public void setProxyCount(int proxyCount) {
		this.proxyCount = proxyCount;
	}

}
