package org.genia.trainchecker.core;

import java.util.Date;

public class UzTicketsRequest {
    private UzStation from;
    private UzStation till;
    private Date date;
    private String train;
    private PlaceType placeType;

    public UzTicketsRequest() {
        placeType = PlaceType.ANY;
        from = new UzStation();
        till = new UzStation();
    }

    public UzStation getFrom() {
        return from;
    }

    public void setFrom(UzStation from) {
        this.from = from;
    }

    public UzStation getTill() {
        return till;
    }

    public void setTill(UzStation till) {
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
