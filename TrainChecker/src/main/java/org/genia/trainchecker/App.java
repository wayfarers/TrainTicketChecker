package org.genia.trainchecker;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class App 
{
    public static void main( String[] args ) throws ParseException {
    	TrainTicketChecker checker = new TrainTicketChecker();
    	
    	TicketsRequest request = new TicketsRequest();
    	request.from.station = "Львів";
    	request.from.station_id = "2218000";
    	request.till.station = "Київ";
    	request.till.station_id = "2200001";
    	request.date = new SimpleDateFormat("dd.MM.yyyy").parse("25.09.2015");
    	
    	checker.checkTickets(request);
    	
    	
    }
}
