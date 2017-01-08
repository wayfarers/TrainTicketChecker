package org.genia.trainchecker.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
class UzTicketsResponseError {
    private boolean error;
    private String data;
    @JsonProperty("value")
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
    String getErrorDescription() {
        return errorDescription;
    }
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }


}
