package com.rest.implementations;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Network {
	public String request(String endpoint) throws Exception {
		StringBuilder sb = new StringBuilder();
		URL url = new URL(endpoint);

		// Open a connection with URL
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		try {
			// Read in the bytes
			InputStream inputStream = urlConnection.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

			// Read them as characters
			InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			// Read one line at a time
			String inputLine = bufferedReader.readLine();
			while (inputLine != null) {
				// add this to output
				sb.append(inputLine);
				inputLine = bufferedReader.readLine();
			}
		} finally {
			urlConnection.disconnect();
		}

		return sb.toString();
	}
}
