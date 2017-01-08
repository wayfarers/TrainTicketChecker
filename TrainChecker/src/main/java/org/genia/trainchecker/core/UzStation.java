package org.genia.trainchecker.core;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonProperty;


public class UzStation implements Comparable<UzStation>{
    @JsonProperty("station_id")
    private String stationId;

    @JsonProperty("station")
    private String name;

    private long date;
    @JsonProperty("src_date")
    private Date srcDate;

    public String getStationId() {
        return stationId;
    }
    public void setStationId(String stationId) {
        this.stationId = stationId;
    }
    public String getName() {
        return name;
    }
    public void setName(String station) {
        this.name = station;
    }
    public long getDate() {
        return date;
    }
    public void setDate(long date) {
        this.date = date;
    }
    public Date getSrcDate() {
        return srcDate;
    }
    public void setSrcDate(Date srcDate) {
        this.srcDate = srcDate;
    }

    public static Map<String, UzStation> listToMap(List<UzStation> list) {
        Map<String, UzStation> map = new TreeMap<>();
        for (UzStation station : list) {
            map.put(station.getName(), station);
        }
        return map;
    }

    /**
     * JSON setter for 'station' field. Needed due to different station presentation in different places on the server side.
     * @param title
     */
    public void setTitle(String title) {
        this.name = title;
    }

    @Override
    public int compareTo(UzStation o) {
        return this.name.compareTo(o.name);
    }
}
