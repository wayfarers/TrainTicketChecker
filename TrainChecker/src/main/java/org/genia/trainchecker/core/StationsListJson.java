package org.genia.trainchecker.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class StationsListJson {
    boolean error;
    @JsonProperty("value")
    List<UzStation> stations = new ArrayList<>();

    public boolean isError() {
        return error;
    }
    public void setError(boolean error) {
        this.error = error;
    }
    public List<UzStation> getStations() {
        return stations;
    }
    public void setStations(List<UzStation> stations) {
        this.stations = stations;
    }
}
