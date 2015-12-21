package org.genia.trainchecker.entities;

import java.util.Date;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Named
@Entity
public class Station {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "stationId")
	private Integer id;
	
	private String stationName;
	
	@Column(name = "station_id_uz")
	private String stationIdUz;
	
	private Date src_date;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStationIdUz() {
		return stationIdUz;
	}
	public void setStationIdUz(String stationIdUz) {
		this.stationIdUz = stationIdUz;
	}
	public Date getSrc_date() {
		return src_date;
	}
	public void setSrc_date(Date src_date) {
		this.src_date = src_date;
	}
}
