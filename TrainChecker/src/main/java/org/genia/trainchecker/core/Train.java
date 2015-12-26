package org.genia.trainchecker.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Train {
	String num;
	int model;
	int category;
	@JsonProperty("reserve_error")
	String reserveError;
	
	// Provided by UZ, but ignored so far.
	@JsonProperty("travel_time")
	String travelTime;
	
	Station from;
	Station till;
	@JsonProperty("types")
	List<Place> places = new ArrayList<>();
	
	public int getTotalPlaces() {
		int total = 0;
		for (Place place : places) {
			total += place.places;
		}
		return total;
	}
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public int getModel() {
		return model;
	}
	public void setModel(int model) {
		this.model = model;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public Station getFrom() {
		return from;
	}
	public void setFrom(Station from) {
		this.from = from;
	}
	public Station getTill() {
		return till;
	}
	public void setTill(Station till) {
		this.till = till;
	}
	public List<Place> getPlaces() {
		return places;
	}
	public void setPlaces(List<Place> places) {
		this.places = places;
	} 
	public String getReserveError() {
		return reserveError;
	}
	public void setReserveError(String reserveError) {
		this.reserveError = reserveError;
	}
}
