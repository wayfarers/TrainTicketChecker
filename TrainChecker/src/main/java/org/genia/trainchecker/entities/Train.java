package org.genia.trainchecker.entities;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Named
@Entity
@Table(name="Train")
public class Train {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "trainId")
	private Integer id;
	private String trainNum;
	private Integer model;
	private Integer category;
	private String from;
	private String till;
	
	
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
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTill() {
		return till;
	}
	public void setTill(String till) {
		this.till = till;
	}
	
}
