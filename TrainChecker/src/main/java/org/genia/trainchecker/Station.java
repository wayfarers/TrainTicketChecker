package org.genia.trainchecker;

import org.codehaus.jackson.annotate.JsonProperty;

public class Station {
	String station_id;
	@JsonProperty("title")
	String station;
	long date;
	String src_date;
	
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
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
	
	
}
