package org.genia.trainchecker;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TrainTicketChecker {
	public static final String URL = "http://booking.uz.gov.ua/purchase/search/";
	String url2 = "http://booking.uz.gov.ua/";
	private String token;
	private HttpClient client = new HttpClient();
	
	public TrainTicketChecker() {
	}
	
	public String checkTickets() {
		String jsonResp = null;
		try {
			jsonResp = sendPost();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			System.out.println("Read timeout");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JsonParser parser = new JsonParser();
	    JsonObject mainObject = parser.parse(jsonResp).getAsJsonObject();
	    JsonArray pItem = mainObject.getAsJsonArray("value");
	    for (JsonElement user : pItem) {
	        JsonObject userObject = user.getAsJsonObject();
	        if (userObject.get("num").getAsString().contains("292")) {
	        	System.out.println("Train# " + userObject.get("num").getAsString());
	        	JsonArray places = userObject.getAsJsonArray("types");
	        	for (JsonElement item : places) {
					System.out.print(item.getAsJsonObject().get("title").getAsString());
					System.out.println(" - " + item.getAsJsonObject().get("places").getAsString());
				}
//	            return null;
	        }
	    }
		
		return jsonResp;
	}
	
	private void parseToken(String html) {
	    String adapter = "var token;localStorage={setItem:function(key, value){if(key==='gv-token')token=value}};";
	    Pattern pattern = Pattern.compile("\\$\\$_=.*~\\[\\];.*\"\"\\)\\(\\)\\)\\(\\);");
	    Matcher matcher = pattern.matcher(html);
	    if (matcher.find()) {
	        String obfuscated = matcher.group(0);
//	        System.out.println(obfuscated);
	        ScriptEngineManager factory = new ScriptEngineManager();
	        ScriptEngine engine = factory.getEngineByName("JavaScript");
	        try {
	            engine.eval(adapter + obfuscated);
	        } catch (ScriptException e) {
	            e.printStackTrace();
	        }
	        token = engine.get("token").toString();
	    }
	}
	
	private String sendPost() throws HttpException, IOException {
		
		String html = "";
		GetMethod get = new GetMethod(url2);
//		get.setRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		get.setRequestHeader("Accept-Encoding", "gzip, deflate");
//		get.setRequestHeader("Accept-Language", "uk,ru;q=0.8,en-US;q=0.5,en;q=0.3");
//		get.setRequestHeader("Cache-Control", "max-age=0");
//		get.setRequestHeader("Connection", "keep-alive");
//		get.setRequestHeader("Host", "booking.uz.gov.ua");
//		get.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
		client.executeMethod(get);
		
		
		int statusCodeInit = client.executeMethod(get);
		if (statusCodeInit != HttpStatus.SC_OK) {
			System.out.println("Get method failed: " + get.getStatusLine());
			return null;
		} else {
			html = get.getResponseBodyAsString();
		}
		parseToken(html);
//		System.out.println(token);
		
		client.getHttpConnectionManager().getParams().setSoTimeout(10000);
		PostMethod post = new PostMethod(URL);
		
		post.addRequestHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.addRequestHeader("Accept-Encoding", "gzip, deflate");
		post.addRequestHeader("Accept-Language", "uk,ru;q=0.8,en-US;q=0.5,en;q=0.3");
		post.addRequestHeader("Cache-Control", "no-cache");
		post.addRequestHeader("Connection", "keep-alive");
		post.addRequestHeader("Content-Length", "202");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addRequestHeader("GV-Ajax", "1");
		post.addRequestHeader("GV-Referer", "http://booking.uz.gov.ua/");
		post.addRequestHeader("GV-Screen", "1680x1050");
		post.addRequestHeader("GV-Token", token);	//?
		post.addRequestHeader("GV-Unique-Host", "1");
		post.addRequestHeader("Host", "booking.uz.gov.ua");
		post.addRequestHeader("Pragma", "no-cache");
		post.addRequestHeader("Referer", "http://booking.uz.gov.ua/");
		post.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
		
		post.addParameter("another_ec", "0");
		post.addParameter("date_dep", "24.08.2015");
		post.addParameter("search", "");
		post.addParameter("station_from", "Львів");
		post.addParameter("station_id_from", "2218000");
		post.addParameter("station_id_till", "2200001");
		post.addParameter("station_till", "Київ");
		post.addParameter("time_dep", "00:00");
		post.addParameter("time_dep_till", "");
		
		int statusCode = client.executeMethod(post);
		if (statusCode != HttpStatus.SC_OK) {
			System.out.println("Post method failed: " + post.getStatusLine());
			return null;
		}
		
		return post.getResponseBodyAsString();
	}
}
