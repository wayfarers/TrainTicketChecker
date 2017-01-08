package org.genia.trainchecker.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.genia.trainchecker.config.JsonOptions;
import org.genia.trainchecker.core.PlaceType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.genia.trainchecker.core.UzStation;

@Named
@Entity
public class TicketsRequest {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ticketsRequestId")
    private Integer id;
    private Date tripDate;

    @Transient
    private TicketsResponse lastResponse;

    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

    @ManyToOne
    @JoinColumn(name = "fromStation")
    private Station from;

    @ManyToOne
    @JoinColumn(name = "toStation")
    private Station to;

    @OneToMany(mappedBy="ticketsRequest")
    @JsonManagedReference
    private List<TicketsResponse> responses = new ArrayList<>();

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
    public List<TicketsResponse> getResponses() {

        if (JsonOptions.isIgnored("needResponses")) {
            return null;
        }

        return responses;
    }
    public void setResponses(List<TicketsResponse> responses) {
        this.responses = responses;
        sortResponses();
    }
    public void setFrom(UzStation from) {
        Station st = new Station();
        st.setStationName(from.getName());
        st.setStationIdUz(from.getStationId());
        st.setSrc_date(from.getSrcDate());
        this.from = st;
    }

    public void setTo(UzStation to) {
        Station st = new Station();
        st.setStationName(to.getName());
        st.setStationIdUz(to.getStationId());
        st.setSrc_date(to.getSrcDate());
        this.to = st;
    }
    private void sortResponses() {
        Collections.sort(responses, new Comparator<TicketsResponse>() {
            @Override
            public int compare(TicketsResponse o1, TicketsResponse o2) {
                return (-1) * o1.getTime().compareTo(o2.getTime());
            }
        });
    }

    public TicketsResponse getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(TicketsResponse lastResponse) {
        this.lastResponse = lastResponse;
    }
}
