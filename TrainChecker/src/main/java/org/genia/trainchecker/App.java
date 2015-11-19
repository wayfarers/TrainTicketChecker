package org.genia.trainchecker;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

public class App 
{
    public static void main( String[] args ) throws ParseException, HttpException, IOException {
    	TrainTicketChecker checker = new TrainTicketChecker();
    	List<Station> list = checker.getAllStations();
    	for (Station station : list) {
			System.out.println(station.getStation() + " - id: " + station.getStationId());
		}
    	
//    	TicketsRequest request = new TicketsRequest();
//    	request.from.station = "Львів";
//    	request.from.station_id = "2218000";
//    	request.till.station = "Київ";
//    	request.till.station_id = "2200001";
//    	request.date = new SimpleDateFormat("dd.MM.yyyy").parse("25.09.2015");
//    	
//    	checker.checkTickets(request);
    	
    	
    }
}
