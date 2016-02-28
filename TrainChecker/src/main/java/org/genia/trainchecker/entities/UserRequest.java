package org.genia.trainchecker.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.genia.trainchecker.core.PlaceType;

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
	
	public List<PlaceType> getPlaceTypesAsList() {
		List<PlaceType> typesList = new ArrayList<>();
		if (placeTypes != null) {
			String [] types = placeTypes.split(",");
			for (String string : types) {
				typesList.add(PlaceType.valueOf(string));
			}
		}
		return typesList;
	}
	
	public Boolean isExpired() {
		GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Kiev"));
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime().after(request.getTripDate());
	}
}
