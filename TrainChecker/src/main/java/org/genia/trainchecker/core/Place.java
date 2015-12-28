package org.genia.trainchecker.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Place {
	String title;
	
	@JsonProperty("letter")
	PlaceType placeType;
	int places;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PlaceType getPlaceType() {
		return placeType;
	}

	public void setPlaceType(PlaceType type) {
		this.placeType = type;
	}

	public int getPlaces() {
		return places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}
	
	@JsonSetter
	public void setPlaceType(String type) {
		switch (type) {
		case "Л":
			placeType = PlaceType.LUX;
			break;
		case "П":
			placeType = PlaceType.PLATZ;
			break;
		case "К":
			placeType = PlaceType.COUPE;
			break;
		case "С1":
			placeType = PlaceType.SIT1;
			break;
		case "С2":
			placeType = PlaceType.SIT2;
			break;
		case "С3":
			placeType = PlaceType.SIT3;
			break;
		case "М":
			placeType = PlaceType.SOFT;
			break;
		default:
			throw new RuntimeException("Unknown place type with letter " + type + "and title" + title + ".");
		}
	}
}
