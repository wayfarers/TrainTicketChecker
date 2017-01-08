package org.genia.trainchecker.entities;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Named
@Entity
public class TicketsResponseItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "responseItemId")
    private Integer id;
    private String reservationError;

    @ManyToOne
    @JoinColumn(name = "trainId")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "ticketsResponseId")
    @JsonBackReference
    private TicketsResponse ticketsResponse;

    @OneToMany(mappedBy = "ticketsResponseItem", cascade = CascadeType.PERSIST, fetch=FetchType.EAGER)
    @JsonManagedReference
    private List<Place> availablePlaces = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReservationError() {
        return reservationError;
    }

    public void setReservationError(String reservationError) {
        this.reservationError = reservationError;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public TicketsResponse getTicketsResponse() {
        return ticketsResponse;
    }

    public void setTicketsResponse(TicketsResponse ticketsResponse) {
        this.ticketsResponse = ticketsResponse;
    }

    public List<Place> getAvailablePlaces() {
        return availablePlaces;
    }

    public void setAvailablePlaces(List<Place> availablePlaces) {
        this.availablePlaces = availablePlaces;
    }
}
