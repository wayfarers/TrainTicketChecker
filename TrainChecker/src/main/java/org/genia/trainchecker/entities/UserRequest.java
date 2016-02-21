package org.genia.trainchecker.entities;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Named
@Entity
public class UserRequest {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "userRequestId")
	private Integer id;
	private String trainNum;
	private Boolean active;
	private String placeTypes;
	
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "ticketsRequestId")
	private TicketsRequest request;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTrainNum() {
		return trainNum;
	}

	public void setTrainNum(String trainNum) {
		this.trainNum = trainNum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TicketsRequest getRequest() {
		return request;
	}

	public void setRequest(TicketsRequest request) {
		this.request = request;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getPlaceTypes() {
		return placeTypes;
	}

	public void setPlaceTypes(String placeTypes) {
		this.placeTypes = placeTypes;
	}
}
