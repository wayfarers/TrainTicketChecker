package org.genia.trainchecker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.genia.trainchecker.core.Station;
import org.genia.trainchecker.core.TicketsRequest;
import org.genia.trainchecker.core.TicketsResponse;
import org.genia.trainchecker.core.Train;
import org.genia.trainchecker.core.TrainTicketChecker;
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
		List<Station> stations = checker.getAllStations();
		assertTrue(stations.size() > 1000);
		Map<String, Station> map = Station.listToMap(stations);
		assertTrue(map.containsKey("Київ"));
	}
	
	@Test
	public void listToMapTest() throws Exception {
		List<Station> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Station station = new Station();
			station.setName("Station " + i);
			station.setStationId("" + i);
			list.add(station);
		}
		Map<String, Station> map = Station.listToMap(list);
		assertTrue(map.size() == 10);
		assertTrue(map.containsKey("Station 0"));
		assertTrue(map.containsKey("Station 5"));
		assertTrue(map.containsKey("Station 9"));
		assertEquals("5", map.get("Station 5").getStationId());
	}
	
	@Test
	public void checkTicketsErrorTest() throws Exception {
		Map<String, Station> map = checker.getStationsAsMap();
		TicketsRequest request = new TicketsRequest();
		request.setFrom(map.get("Київ"));
		request.setTill(map.get("Київ"));
		
		GregorianCalendar cal = new GregorianCalendar();
		cal.add(Calendar.DATE, 1);
		request.setDate(cal.getTime());
		TicketsResponse response = checker.checkTickets(request);
		assertTrue(response.isError());
		assertEquals("Станції відправлення та призначення співпадають", response.getErrorDescription());
	}
	
	@Test
	public void checkTicketsTest() throws Exception {
		LocalDate dateFrom = LocalDate.now();
		dateFrom.plusDays(2);
		Map<String, Station> map = checker.getStationsAsMap();
		TicketsRequest request = new TicketsRequest();
		request.setFrom(map.get("Київ"));
		request.setTill(map.get("Львів"));
		request.setDate(dateFrom.toDate());
		TicketsResponse response = checker.checkTickets(request);
		for (Train train : response.getTrains()) {
			LocalDate date = LocalDate.fromDateFields(train.getFrom().getSrcDate());
			assertTrue(dateFrom.equals(date));
		}
	}
}
