package org.genia.trainchecker.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UzTicketsResponse {
    private boolean error;
    private String data;
    @JsonProperty("value")
    private List<UzTrain> trains = new ArrayList<>();
    private String errorDescription;

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
    void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
