package org.genia.trainchecker;

import java.util.Date;

public class TicketsRequest {
	Station from;
	Station till;
	Date date;
	String train;
	PlaceType placeType;
	
	public TicketsRequest() {
		placeType = PlaceType.ANY;
		from = new Station();
		till = new Station();
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
		this.train = train;
	}

	public PlaceType getPlaceType() {
		return placeType;
	}

	public void setPlaceType(PlaceType placeType) {
		this.placeType = placeType;
	}
}
