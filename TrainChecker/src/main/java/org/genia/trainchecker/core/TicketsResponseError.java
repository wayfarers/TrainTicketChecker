package org.genia.trainchecker.core;

import org.codehaus.jackson.annotate.JsonProperty;

public class TicketsResponseError {
	boolean error;
	String data;
	@JsonProperty("value")
	String errorDescription;
	
	
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
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	
}
