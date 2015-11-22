package org.genia.trainchecker.core;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class TicketsResponse {
	boolean error;
	String data;
	@JsonProperty("value")
	List<Train> trains = new ArrayList<>();
	
	
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public List<Train> getTrains() {
		return trains;
	}
	public void setTrains(List<Train> trains) {
		this.trains = trains;
	}
}
