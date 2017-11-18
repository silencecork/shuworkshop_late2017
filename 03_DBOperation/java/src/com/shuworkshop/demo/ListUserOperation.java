package com.shuworkshop.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListUserOperation implements IOperation {
	HashMap<String, String> params = new HashMap<String, String>();
	static final String API_ENDPOINT = API.ENDPOINT + "/userList";

	@Override
	public void doOperation() {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(API_ENDPOINT);
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
			ArrayList<User> users = new ArrayList<User>();
			JSONArray jsonAry = json.optJSONArray("data");
			for (int i = 0; i <jsonAry.length(); i++) {
				JSONObject userJson = jsonAry.optJSONObject(i);
				User user = new User();
				user.name = userJson.optString("username");
				user.password = userJson.optString("password");
				user.realname = userJson.optString("realname");
				users.add(user);
			}
			printAllUser(users);
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
		System.out.println("We will list user...");
	}
	
	private static void printAllUser(ArrayList<User> list) {
		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			System.out.println(user);
			System.out.println("-----------------------");
		}
	}

}
