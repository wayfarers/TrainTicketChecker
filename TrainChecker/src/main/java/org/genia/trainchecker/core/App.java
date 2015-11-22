package org.genia.trainchecker.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.httpclient.HttpException;

public class App 
{
    public static void main( String[] args ) throws ParseException, HttpException, IOException {
    	TrainTicketChecker checker = new TrainTicketChecker();
//    	List<Station> list = checker.getAllStations();
//    	for (Station station : list) {
//			System.out.println(station.getStation() + " - id: " + station.getStationId());
//		}
    	
    	TicketsRequest request = new TicketsRequest();
    	request.from.setStation("Львів"); 
    	request.from.setStationId("2218000"); 
    	request.till.setStation("Київ");
    	request.till.setStationId("2200001");
    	request.date = new SimpleDateFormat("dd.MM.yyyy").parse("25.12.2015");
    	
    	TicketsResponse response = checker.checkTickets(request);
    	
    	for (Train train : response.trains) {
			System.out.printf("%s\t%s - %s, %d free places total%n", train.num, train.from.getStation(), train.till.getStation(), train.getTotalPlaces());
		}
    	
    	
    }
}