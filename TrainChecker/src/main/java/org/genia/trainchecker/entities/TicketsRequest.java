package org.genia.trainchecker.entities;

import java.util.Date;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.genia.trainchecker.core.PlaceType;

@Named
@Entity
public class TicketsRequest {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ticketsRequestId")
	private Integer id;
	private Date tripDate;
	
	@Enumerated(EnumType.STRING)
	private PlaceType placeType;
	
	@ManyToOne
	@JoinColumn(name = "fromStation")
	private Station from;
	
	@ManyToOne
	@JoinColumn(name = "toStation")
	private Station to;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getTripDate() {
		return tripDate;
	}
	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}
	public PlaceType getPlaceType() {
		return placeType;
	}
	public void setPlaceType(PlaceType placeType) {
		this.placeType = placeType;
	}
	public Station getFrom() {
		return from;
	}
	public void setFrom(Station from) {
		this.from = from;
	}
	public Station getTo() {
		return to;
	}
	public void setTo(Station to) {
		this.to = to;
	}
	
	public void setFrom(org.genia.trainchecker.core.Station from) {
		Station st = new Station();
		st.setStationName(from.getName());
		st.setStationIdUz(from.getStationId());
		st.setSrc_date(from.getSrcDate());
		this.from = st;
	}
	
	public void setTo(org.genia.trainchecker.core.Station to) {
		Station st = new Station();
		st.setStationName(to.getName());
		st.setStationIdUz(to.getStationId());
		st.setSrc_date(to.getSrcDate());
		this.to = st;
	}
}