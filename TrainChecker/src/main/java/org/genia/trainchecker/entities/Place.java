package org.genia.trainchecker.entities;

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

import com.fasterxml.jackson.annotation.JsonBackReference;

@Named
@Entity
public class Place {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "placeId")
	private Integer id;
	private String title;
	
	@Enumerated(EnumType.STRING)
	private PlaceType placeType; //TODO: the same question as in TicketsRequestEntity
	private Integer placesAvailable;
	
	@ManyToOne
	@JoinColumn(name = "responseItemId")
	@JsonBackReference
	private TicketsResponseItem ticketsResponseItem;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPlacesAvailable() {
		return placesAvailable;
	}

	public void setPlacesAvailable(Integer placesAvailable) {
		this.placesAvailable = placesAvailable;
	}

	public TicketsResponseItem getTicketsResponseItem() {
		return ticketsResponseItem;
	}

	public void setTicketsResponseItem(TicketsResponseItem ticketsResponse) {
		this.ticketsResponseItem = ticketsResponse;
	}

	public PlaceType getPlaceType() {
		return placeType;
	}

	public void setPlaceType(PlaceType placeType) {
		this.placeType = placeType;
	}
}
