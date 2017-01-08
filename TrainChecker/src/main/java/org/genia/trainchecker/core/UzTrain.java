package org.genia.trainchecker.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UzTrain {
    private String num;
    private int model;
    private int category;
    @JsonProperty("reserve_error")
    private String reserveError;

    // Provided by UZ, but ignored so far.
    @JsonProperty("travel_time")
    String travelTime;

    private UzStation from;
    private UzStation till;
    @JsonProperty("types")
    private List<UzPlace> places = new ArrayList<>();

    public int getTotalPlaces() {
        int total = 0;
        for (UzPlace place : places) {
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
    public List<UzPlace> getPlaces() {
        return places;
    }
    public void setPlaces(List<UzPlace> places) {
        this.places = places;
    }
    public String getReserveError() {
        return reserveError;
    }
    public void setReserveError(String reserveError) {
        this.reserveError = reserveError;
    }
}
