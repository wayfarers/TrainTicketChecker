package org.genia.trainchecker.entities;

import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Named
@Entity
@Table(name="TicketsResponse")
public class TicketsResponse {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ticketsResponseId")
	private Integer id;
	private String data;
	private String errorDescription;
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	private Integer requestLatency;
	
	@ManyToOne
	@JoinColumn(name = "ticketsRequestId")
	private TicketsRequest ticketsRequest;
	
	@OneToMany(mappedBy = "ticketsResponse")
	private List<TicketsResponseItem> items;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getRequestLatency() {
		return requestLatency;
	}
	public void setRequestLatency(Integer requestLatency) {
		this.requestLatency = requestLatency;
	}
	public TicketsRequest getTicketsRequest() {
		return ticketsRequest;
	}
	public void setTicketsRequest(TicketsRequest ticketsRequest) {
		this.ticketsRequest = ticketsRequest;
	}
	public List<TicketsResponseItem> getItems() {
		return items;
	}
	public void setItems(List<TicketsResponseItem> items) {
		this.items = items;
	}
}
