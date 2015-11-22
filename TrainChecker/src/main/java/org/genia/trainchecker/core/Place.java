package org.genia.trainchecker.core;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSetter;

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

		default:
			break;
		}
	}
}
