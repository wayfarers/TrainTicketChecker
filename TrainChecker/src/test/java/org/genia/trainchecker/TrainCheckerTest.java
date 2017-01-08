package org.genia.trainchecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.genia.trainchecker.core.*;
import org.genia.trainchecker.core.UzStation;
import org.joda.time.LocalDate;
import org.junit.BeforeClass;
import org.junit.Test;


public class TrainCheckerTest {
	static TrainTicketChecker checker;
	
	@BeforeClass
	public static void setUp() {
		checker = new TrainTicketChecker();
	}
	
	@Test
	public void getAllStationsTest() throws Exception {
		List<UzStation> stations = checker.getAllStations();
		assertTrue(stations.size() > 1000);
		Map<String, UzStation> map = UzStation.listToMap(stations);
		assertTrue(map.containsKey("Київ"));
	}
	
	@Test
	public void listToMapTest() throws Exception {
		List<UzStation> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			UzStation station = new UzStation();
			station.setName("Station " + i);
			station.setStationId("" + i);
			list.add(station);
		}
		Map<String, UzStation> map = UzStation.listToMap(list);
		assertTrue(map.size() == 10);
		assertTrue(map.containsKey("Station 0"));
		assertTrue(map.containsKey("Station 5"));
		assertTrue(map.containsKey("Station 9"));
		assertEquals("5", map.get("Station 5").getStationId());
	}
	
	@Test
	public void checkTicketsErrorTest() throws Exception {
		Map<String, UzStation> map = checker.getStationsAsMap();
		UzTicketsRequest request = new UzTicketsRequest();
		request.setFrom(map.get("Київ"));
		request.setTill(map.get("Київ"));
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, 1);
		request.setDate(cal.getTime());
		UzTicketsResponse response = checker.checkTickets(request);
		assertTrue(response.isError());
		assertEquals("Станції відправлення та призначення співпадають", response.getErrorDescription());
	}
	
	@Test
	public void checkTicketsTest() throws Exception {
		LocalDate dateFrom = LocalDate.now();
		dateFrom.plusDays(2);
		Map<String, UzStation> map = checker.getStationsAsMap();
		UzTicketsRequest request = new UzTicketsRequest();
		request.setFrom(map.get("Київ"));
		request.setTill(map.get("Львів"));
		request.setDate(dateFrom.toDate());
		UzTicketsResponse response = checker.checkTickets(request);
		for (UzTrain train : response.getTrains()) {
			LocalDate date = LocalDate.fromDateFields(train.getFrom().getSrcDate());
			assertTrue(dateFrom.equals(date));
		}
	}
}
