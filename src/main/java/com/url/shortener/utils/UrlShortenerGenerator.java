package com.url.shortener.utils;

import java.util.HashMap;
import java.util.Random;

public class UrlShortenerGenerator {
	private static HashMap<String, String> keyMap; // key-url map
	private static HashMap<String, String> valueMap;// url-key map to quickly check
	private static char myChars[]; // This array is used for character to number
	// mapping
	private static Random myRand; // Random object used to generate random integers
	private static int keyLength; // the key length in URL defaults to 8
	private static String domain;
	
	static {
		keyMap = new HashMap<String, String>();
		valueMap = new HashMap<String, String>();
		myRand = new Random();
		keyLength = 8;
		myChars = new char[62];
		for (int i = 0; i < 62; i++) {
			int j = 0;
			if (i < 10) {
				j = i + 48;
			} else if (i > 9 && i <= 35) {
				j = i + 55;
			} else {
				j = i + 61;
			}
			myChars[i] = (char) j;
		}
		domain = "http://fkt.in";
	}
	
	public static String expandURL(String shortURL) {
		String longURL = "";
		String key = shortURL.substring(domain.length() + 1);
		longURL = keyMap.get(key);
		return longURL;
	}
	
	public static String shortenURL(String longURL) {
		String shortURL = "";
		if (validateURL(longURL)) {
			longURL = sanitizeURL(longURL);
			if (valueMap.containsKey(longURL)) {
				shortURL = domain + "/" + valueMap.get(longURL);
			} else {
				shortURL = domain + "/" + getKey(longURL);
			}
		}
		// add http part
		return shortURL;
	}
	
	static boolean validateURL(String url) {
		return true;
	}
	
	static String sanitizeURL(String url) {
		if (url.substring(0, 7).equals("http://"))
			url = url.substring(7);

		if (url.substring(0, 8).equals("https://"))
			url = url.substring(8);

		if (url.charAt(url.length() - 1) == '/')
			url = url.substring(0, url.length() - 1);
		return url;
	}
	
	private static String getKey(String longURL) {
		String key;
		key = generateKey();
		keyMap.put(key, longURL);
		valueMap.put(longURL, key);
		return key;
	}
	
	// generateKey
		private static String generateKey() {
			String key = "";
			boolean flag = true;
			while (flag) {
				key = "";
				for (int i = 0; i <= keyLength; i++) {
					key += myChars[myRand.nextInt(62)];
				}
				// System.out.println("Iteration: "+ counter + "Key: "+ key);
				if (!keyMap.containsKey(key)) {
					flag = false;
				}
			}
			return key;
		}


}
