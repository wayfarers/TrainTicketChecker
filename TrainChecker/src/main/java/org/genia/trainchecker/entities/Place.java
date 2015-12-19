package org.genia.trainchecker.entities;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Named
@Entity
@Table(name="Place")
public class Place {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "placeId")
	private Integer id;
	private String title;
	private String placeType; //TODO: the same question as in TicketsRequestEntity
	private Integer placesAvailable;
	
	@ManyToOne
	@JoinColumn(name = "responseItemId")
	private TicketsResponseItem ticketsResponse;

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

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public Integer getPlacesAvailable() {
		return placesAvailable;
	}

	public void setPlacesAvailable(Integer placesAvailable) {
		this.placesAvailable = placesAvailable;
	}

	public TicketsResponseItem getTicketsResponse() {
		return ticketsResponse;
	}

	public void setTicketsResponse(TicketsResponseItem ticketsResponse) {
		this.ticketsResponse = ticketsResponse;
	}
}
