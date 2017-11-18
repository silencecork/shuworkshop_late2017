package com.shuworkshop.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

public class DeleteUserOperation implements IOperation {
	HashMap<String, String> params = new HashMap<String, String>();
	static final String API_ENDPOINT = API.ENDPOINT + "/deleteUser";

	@Override
	public void doOperation() {
		HttpURLConnection conn = null;
		try {
			String apiURL = API_ENDPOINT + "?username=" + params.get("username");
			URL url = new URL(apiURL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			int responseCode = conn.getResponseCode();
			System.out.println("statusCode: " + responseCode);
			if (responseCode != HttpURLConnection.HTTP_OK) {
				String errorMessage = Utils.streamToString(conn.getErrorStream());
				System.out.println("error: " + errorMessage);
				return;
			}
			InputStream stream = conn.getInputStream();
			String content = Utils.streamToString(stream);
			
			
			JSONObject json = new JSONObject(content);
			System.out.println(json);			
		
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		
		
	}

	@Override
	public void askQuestion() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("User Name?");
		String username = scanner.nextLine();
		System.out.println("User: " + username);
		
		params.put("username", username);
		
		scanner.close();
	}

}
