package com.shuworkshop.demo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	
	public static void main(String[] args) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL("http://127.0.0.1:3000/users");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			int responseCode = conn.getResponseCode();
			System.out.println("statusCode: " + responseCode);
			if (responseCode != HttpURLConnection.HTTP_OK) {
				String errorMessage = streamToString(conn.getErrorStream());
				System.out.println("error: " + errorMessage);
				return;
			}
			InputStream stream = conn.getInputStream();
			String content = streamToString(stream);
			
			
			JSONObject json = new JSONObject(content);
			System.out.println(json);
			
			ArrayList<User> objList = new ArrayList<User>();
			
			JSONArray userList = json.optJSONArray("userList");
			for (int i = 0; i < userList.length(); i++) {
				JSONObject userJson = userList.optJSONObject(i);
				User music = parseResult(userJson);
				objList.add(music);
			}
			
			printAllUsers(objList);
			
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
	
	private static String streamToString(InputStream in) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		for (;;) {
		    int nread = in.read(buf, 0, buf.length);
		    if (nread <= 0) {
		        break;
		    }
		    baos.write(buf, 0, nread);
		}
		in.close();
		baos.close();
		byte[] bytes = baos.toByteArray();
		return new String(bytes, StandardCharsets.UTF_8);
	}
	
	private static User parseResult(JSONObject userJson) {
		User user = new User();
		
		user.name = userJson.optString("name");
		user.password = userJson.optString("password");
		
		return user;
	}
	
	private static void printAllUsers(ArrayList<User> list) {
		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			System.out.println(user);
			System.out.println("-----------------------");
		}
	}
}
