package org.genia.trainchecker.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UzTicketsResponse {
	boolean error;
	String data;
	@JsonProperty("value")
	List<UzTrain> trains = new ArrayList<>();
	String errorDescription;
	
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
	public List<UzTrain> getTrains() {
		return trains;
	}
	public void setTrains(List<UzTrain> trains) {
		this.trains = trains;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
