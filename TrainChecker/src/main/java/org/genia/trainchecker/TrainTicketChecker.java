package org.genia.trainchecker;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.apache.commons.httpclient.util.URIUtil;
import org.codehaus.jackson.map.ObjectMapper;

public class TrainTicketChecker {
	public static final String URL = "http://booking.uz.gov.ua/purchase/search/";
	String url2 = "http://booking.uz.gov.ua/";
	private String token;
	private HttpClient client = new HttpClient();
	private Map<String, String> stations;
	
	
	public TrainTicketChecker() {
	}
	
	public String checkTickets(TicketsRequest request) {
		String jsonResp = null;
		TicketsResponse response = null;
		try {
			jsonResp = sendPost(request);
			response = new ObjectMapper().readValue(jsonResp, TicketsResponse.class);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			System.out.println("Read timeout");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Train train : response.trains) {
			System.out.println(train.num);
			System.out.println("\t" + train.from.station + " - " + train.till.station);
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
	
	private String sendPost(TicketsRequest request) throws HttpException, IOException {
		
		String html = "";
		GetMethod get = new GetMethod(url2);
		client.executeMethod(get);
		
		
		int statusCodeInit = client.executeMethod(get);
		if (statusCodeInit != HttpStatus.SC_OK) {
			System.out.println("Get method failed: " + get.getStatusLine());
			return null;
		} else {
			html = get.getResponseBodyAsString();
		}
		parseToken(html);
		
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
		post.addParameter("date_dep", new SimpleDateFormat("dd.MM.yyyy").format(request.date));
		post.addParameter("search", "");
		post.addParameter("station_from", request.from.station);
		post.addParameter("station_id_from", request.from.station_id);
		post.addParameter("station_id_till", request.till.station_id);
		post.addParameter("station_till", request.till.station);
		post.addParameter("time_dep", "00:00");
		post.addParameter("time_dep_till", "");
		
		int statusCode = client.executeMethod(post);
		if (statusCode != HttpStatus.SC_OK) {
			System.out.println("Post method failed: " + post.getStatusLine());
			return null;
		}
		
		return post.getResponseBodyAsString();
	}
	
	public List<Station> getAllStations() throws HttpException, IOException {
		String url = "http://booking.uz.gov.ua/purchase/station/";
		GetMethod get = null;
		String jsonResp = null;
		int [] ukrChars = {1108, 1110, 1111, 1169}; 	//є, і, ї, ґ
		List<Station> stations = new ArrayList<>();
		
		for (int i = 0; i < 32; i++) {
			String currentUrl = URIUtil.encodeQuery(url + (char) (1072 + i));
			get = new GetMethod(currentUrl);	//1072 - rus 'a' in ASCII
			int statusCode = client.executeMethod(get);
			if (statusCode != HttpStatus.SC_OK) {
				System.out.println("Get method failed: " + get.getStatusLine());
				return null;
			}
			jsonResp = get.getResponseBodyAsString();
			StationJson resp = new ObjectMapper().readValue(jsonResp, StationJson.class);
			stations.addAll(resp.getStations());
		}
		return stations;
	}
}
