package org.genia.trainchecker.entities;

import javax.inject.Named;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Named
@Entity
public class Train {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "trainId")
    private Integer id;
    private String trainNum;
    private Integer model;
    private Integer category;

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
    public Station getFrom() {
        return from;
    }
    public void setFrom(Station from) {
        this.from = from;
    }
    public Station getTo() {
        return to;
    }
    public void setTo(Station till) {
        this.to = till;
    }

}
