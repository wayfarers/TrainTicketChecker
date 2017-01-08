package org.genia.trainchecker.core;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrainTicketChecker {
    private final static Logger logger = LoggerFactory.getLogger(TrainTicketChecker.class);
    private static final String URL = "http://booking.uz.gov.ua/purchase/search/";
    private String url2 = "http://booking.uz.gov.ua/";
    private String token;
    private HttpClient client = new HttpClient();
    private List<UzStation> stations = null;

    public TrainTicketChecker() {
        client.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
        client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
        getAllStations();
    }

    public UzTicketsResponse checkTickets(UzTicketsRequest request) {
        String jsonResp;
        UzTicketsResponse response = null;
        UzTicketsResponseError responseError;
        try {
            jsonResp = sendRequest(request);
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                response = mapper.readValue(jsonResp, UzTicketsResponse.class);
            } catch (JsonMappingException e) {
                responseError = new ObjectMapper().readValue(jsonResp, UzTicketsResponseError.class);
                UzTicketsResponse invaildResponse = new UzTicketsResponse();
                invaildResponse.setError(true);
                invaildResponse.setErrorDescription(responseError.getErrorDescription());
                logger.error("UzGovUa error description: " + responseError.getErrorDescription());
                return invaildResponse;
            }
        } catch (HttpException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return response;
    }

    private void parseToken(String html) {
        String adapter = "var token;localStorage={setItem:function(key, value){if(key==='gv-token')token=value}};";
        Pattern pattern = Pattern.compile("\\$\\$_=.*~\\[\\];.*\"\"\\)\\(\\)\\)\\(\\);");
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            String obfuscated = matcher.group(0);
            ScriptEngineManager factory = new ScriptEngineManager(null);
            ScriptEngine engine = factory.getEngineByName("JavaScript");
            try {
                engine.eval(adapter + obfuscated);
            } catch (ScriptException e) {
                logger.error(e.getMessage(), e);
            }
            token = engine.get("token").toString();
        }
    }

    private String sendRequest(UzTicketsRequest request) throws IOException {

        String html;
        GetMethod get = new GetMethod(url2);
        client.executeMethod(get);

        int statusCodeInit = client.executeMethod(get);
        if (statusCodeInit != HttpStatus.SC_OK) {
            logger.error("sendRequest method failed. Get method failed: " + get.getStatusLine());
            return null;
        } else {
            html = IOUtils.toString(get.getResponseBodyAsStream(), "UTF-8");
        }
        parseToken(html);

        client.getHttpConnectionManager().getParams().setSoTimeout(10000);
        PostMethod post = new PostMethod(URL);

        addRequestHeaders(post);
        addRequestParameters(request, post);

        int statusCode = client.executeMethod(post);
        if (statusCode != HttpStatus.SC_OK) {
            logger.error("sendRequest method failed. Post method failed: " + post.getStatusLine());
            return null;
        }

        return IOUtils.toString(post.getResponseBodyAsStream(), "UTF-8");
    }

    private void addRequestHeaders(PostMethod post) {
        post.addRequestHeader("Accept", "*/*");
        post.addRequestHeader("Accept-Encoding", "gzip, deflate");
        post.addRequestHeader("Accept-Language", "uk,ru;q=0.8,en-US;q=0.5,en;q=0.3");
        post.addRequestHeader("Connection", "keep-alive");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        post.addRequestHeader("GV-Ajax", "1");
        post.addRequestHeader("GV-Referer", "http://booking.uz.gov.ua/");
        post.addRequestHeader("GV-Screen", "1920x1080");
        post.addRequestHeader("GV-Token", token);
        post.addRequestHeader("GV-Unique-Host", "1");
        post.addRequestHeader("Host", "booking.uz.gov.ua");
        post.addRequestHeader("Origin", "http://booking.uz.gov.ua");
        post.addRequestHeader("Referer", "http://booking.uz.gov.ua/");
        post.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/39.0");
    }

    private void addRequestParameters(UzTicketsRequest request, PostMethod post) {
        post.addParameter("another_ec", "0");
        post.addParameter("date_dep", new SimpleDateFormat("dd.MM.yyyy").format(request.getDate()));
        post.addParameter("search", "");
        post.addParameter("station_from", request.getFrom().getName());
        post.addParameter("station_id_from", request.getFrom().getStationId());
        post.addParameter("station_id_till", request.getTill().getStationId());
        post.addParameter("station_till", request.getTill().getName());
        post.addParameter("time_dep", "00:00");
        post.addParameter("time_dep_till", "");
    }


    /**
     * Returns list with all stations with their ID's from the server.
     *
     * @return List<Station>
     */
    public List<UzStation> getAllStations() {
        if (stations != null && !stations.isEmpty()) {
            return stations;
        }

        String url = "http://booking.uz.gov.ua/purchase/station/";
        char letter;
        GetMethod get;
        String jsonResp;
        List<UzStation> myStations = new ArrayList<>();

        try {
            for (int i = 0; i < 32; i++) {
                letter = (char) (1072 + i);		//1072 - rus 'a' in ASCII
                String currentUrl = URIUtil.encodeQuery(url + letter);
                get = new GetMethod(currentUrl);
                int statusCode = client.executeMethod(get);
                if (statusCode != HttpStatus.SC_OK) {
                    logger.error("getAllStations method failed. Get method failed: " + get.getStatusLine());
                    return null;
                }
                jsonResp = IOUtils.toString(get.getResponseBodyAsStream(), "UTF-8"); //get.getResponseBodyAsString();
                StationsListJson resp = new ObjectMapper().readValue(jsonResp, StationsListJson.class);
                myStations.addAll(resp.getStations());
            }
        } catch (IOException e) {
            logger.error("Could not retrieve stations." + e.getMessage(), e);
            throw new RuntimeException("Could not retrieve stations", e);
        }
        Collections.sort(myStations);
        return stations = myStations;
    }

    public Map<String, UzStation> getStationsAsMap() {
        return UzStation.listToMap(getAllStations());
    }

    /**
     * Force update of station list from UzGovUa server.
     * @return List
     */
    public List<UzStation> updateStationsList() {
        stations = null;
        stations = getAllStations();
        return stations;
    }

    public void init() {
        stations = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            UzStation station = new UzStation();
            station.setName("station " + i);
            stations.add(station);
        }
    }
}
