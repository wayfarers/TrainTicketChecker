package org.genia.trainchecker.core;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class App {
    public static void main( String[] args ) throws ParseException, IOException {
        TrainTicketChecker checker = new TrainTicketChecker();

        UzTicketsRequest request = new UzTicketsRequest();
        request.getFrom().setName("Київ");
        request.getFrom().setStationId(2200001);
        request.getTill().setName("Львів");
        request.getTill().setStationId(2218000);
        request.setDate(new SimpleDateFormat("dd.MM.yyyy").parse("30.12.2015"));

        UzTicketsResponse response = checker.checkTickets(request);

        for (UzTrain train : response.getTrains()) {
            System.out.printf("%s\t%s - %s, %d free places total%n", train.getNum(), train.getFrom().getName(), train.getTill().getName(), train.getTotalPlaces());
        }
    }
}
