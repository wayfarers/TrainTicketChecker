package org.genia.trainchecker.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

public class Station {
	@JsonProperty("station_id")
	private String stationId;
	private String station;
//	private String title;
	private long date;
	private String src_date;
	
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
	public String getSrc_date() {
		return src_date;
	}
	public void setSrc_date(String src_date) {
		this.src_date = src_date;
	}
	
	public static Map<String, Station> listToMap(List<Station> list) {
		Map<String, Station> map = new HashMap<>();
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
}
