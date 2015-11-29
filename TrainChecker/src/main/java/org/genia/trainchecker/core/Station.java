package org.genia.trainchecker.core;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Station implements Comparable<Station>{
	@JsonProperty("station_id")
	private String stationId;
	private String station;
	private long date;
	@JsonProperty("src_date")
	private Date srcDate;
	
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public Date getSrcDate() {
		return srcDate;
	}
	public void setSrcDate(Date srcDate) {
		this.srcDate = srcDate;
	}
	
	public static Map<String, Station> listToMap(List<Station> list) {
		Map<String, Station> map = new TreeMap<>();
		for (Station station : list) {
			map.put(station.getStation(), station);
		}
		return map;
	}
	
	/**
	 * JSON setter for 'station' field. Needed due to different station presentation in different places on the server side.
	 * @param title
	 */
	public void setTitle(String title) {
		this.station = title;
	}
	
	@Override
	public int compareTo(Station o) {
		return this.station.compareTo(o.station);
	}
}
