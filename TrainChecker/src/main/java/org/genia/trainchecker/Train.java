package org.genia.trainchecker;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.annotations.JsonAdapter;

public class Train {
	String num;
	int model;
	int category;
	
	Station from;
	Station till;
	@JsonProperty("types")
	List<PlaceType> places = new ArrayList<>();
	
	
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
	public List<PlaceType> getPlaces() {
		return places;
	}
	public void setPlaces(List<PlaceType> places) {
		this.places = places;
	} 
}
