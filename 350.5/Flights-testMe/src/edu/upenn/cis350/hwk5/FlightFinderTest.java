package edu.upenn.cis350.hwk5;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FlightFinderTest {
	
	FlightFinder f;
	Flight [] flights;

	
	@Before
	public void setup() {
		f = new FlightFinder();
		flights = AllFlights.getAllFlights();
	}
	
	@Test
	public void testOriginNull() {
		assertEquals(-1, f.findFlights(true, null, "PHL", 500));
		assertEquals(0, FlightFinder.getNumSearches());
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
	}
	
	@Test
	public void testDestNull() {
		assertEquals(-1, f.findFlights(true, "PHL", null, 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(0, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectSimpleExists() {
		assertEquals(1, f.findFlights(true, "PHL", "BOS", 500));
		assertFalse(f.getDirectFlights().isEmpty());
		assertEquals(1, f.getDirectFlights().size());
		assertEquals(flights[0], f.getDirectFlights().get(0));
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(1, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectOriginLowercase() {
		assertEquals(1, f.findFlights(true, "phl", "BOS", 5));
		assertFalse(f.getDirectFlights().isEmpty());
		assertEquals(1, f.getDirectFlights().size());
		assertEquals(flights[0], f.getDirectFlights().get(0));
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(2, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectDestLowercase() {
		assertEquals(1, f.findFlights(true, "PHL", "bos", 500));
		assertFalse(f.getDirectFlights().isEmpty());
		assertEquals(1, f.getDirectFlights().size());
		assertEquals(flights[0], f.getDirectFlights().get(0));
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(3, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectBothLowercase() {
		assertEquals(1, f.findFlights(true, "phl", "bos", 500));
		assertFalse(f.getDirectFlights().isEmpty());
		assertEquals(1, f.getDirectFlights().size());
		assertEquals(flights[0], f.getDirectFlights().get(0));
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(4, FlightFinder.getNumSearches());
	}

	
	@Test
	public void testDirectMixedCase() {
		assertEquals(1, f.findFlights(true, "pHl", "BoS", 500));
		assertFalse(f.getDirectFlights().isEmpty());
		assertEquals(1, f.getDirectFlights().size());
		assertEquals(flights[0], f.getDirectFlights().get(0));
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(5, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectBadOrigin() {
		assertEquals(-1, f.findFlights(true, "XXX", "BoS", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(5, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectBadDest() {
		assertEquals(-1, f.findFlights(true, "BOS", "XXX", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(5, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectBadBoth() {
		assertEquals(-1, f.findFlights(true, "ZZZ", "XXX", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(5, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testNoDirectFlight() {
		assertEquals(0, f.findFlights(true, "BOS", "SEA", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(6, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testNoDirectFlightLowercase() {
		assertEquals(0, f.findFlights(true, "bos", "sea", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(7, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testNoDirectFlightMixedCase() {
		assertEquals(0, f.findFlights(true, "bOs", "SEa", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(8, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectFlightSameAirport() {
		assertEquals(0, f.findFlights(true, "BOS", "BOS", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(9, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testIndirectFlightSameAirport() {
		assertEquals(0, f.findFlights(false, "BOS", "BOS", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(10, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectFlightSameAirportLowerCase() {
		assertEquals(0, f.findFlights(true, "bos", "bos", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(11, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testIndirectFlightSameAirportLowerCase() {
		assertEquals(0, f.findFlights(false, "bos", "bos", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(12, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectFlightSameAirportMixedCase() {
		assertEquals(0, f.findFlights(true, "bOs", "BoS", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(13, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testIndirectFlightSameAirportMixedCase() {
		assertEquals(0, f.findFlights(false, "bOs", "BoS", 500));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(14, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectFlightNegativeTime() {
		assertEquals(-1, f.findFlights(true, "BOS", "PHL", -5));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(14, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testDirectFlightNotEnoughTime() {
		assertEquals(0, f.findFlights(true, "BOS", "PHL", 5));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(15, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testIndirectFlightNegativeTime() {
		assertEquals(-1, f.findFlights(false, "BOS", "PHL", -5));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(15, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testIndirectFlightOneOptionEnoughTime() {
		assertEquals(1, f.findFlights(false, "PHL", "SEA", 225+100+1));
		//flights[4] --> flights[16]
		assertTrue(f.getDirectFlights().isEmpty());
		assertFalse(f.getIndirectFlights().isEmpty());
		assertEquals(1, f.getIndirectFlights().size());
		assertEquals(flights[4], f.getIndirectFlights().get(0)[0]);
		assertEquals(flights[16], f.getIndirectFlights().get(0)[1]);
		assertEquals(16, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testIndirectFlightOneOptionNotEnoughTimeForSecondLeg() {
		assertEquals(0, f.findFlights(false, "PHL", "SEA", 225+100-1));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(17, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testIndirectFlightOneOptionNotEnoughTimeForEitherLeg() {
		assertEquals(0, f.findFlights(false, "PHL", "SEA", 10));
		assertTrue(f.getDirectFlights().isEmpty());
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(18, FlightFinder.getNumSearches());
	}
	
	
	@Test
	public void testIndirectFlightWithDirectOption() {
		assertEquals(2, f.findFlights(false, "PHL", "BOS", 2000));
		assertFalse(f.getDirectFlights().isEmpty());
		assertEquals(1, f.getDirectFlights().size());
		assertEquals(flights[0], f.getDirectFlights().get(0));
		assertFalse(f.getIndirectFlights().isEmpty());
		assertEquals(1, f.getIndirectFlights().size());
		//phl- atl ;  atl-boston
		assertEquals(flights[6], f.getIndirectFlights().get(0)[0]);
		assertEquals(flights[18], f.getIndirectFlights().get(0)[1]);
		assertEquals(19, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testIndirectFlightWithDirectOptionNotEnoughTimeForIndirect() {
		assertEquals(1, f.findFlights(false, "PHL", "BOS", 186));
		assertFalse(f.getDirectFlights().isEmpty());
		assertEquals(1, f.getDirectFlights().size());
		assertEquals(flights[0], f.getDirectFlights().get(0));
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(20, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testTwoOptionsIndirectFlightWithDirectOption() {
		assertEquals(3, f.findFlights(false, "PHL", "ATL", 2000));
		assertEquals(1, f.getDirectFlights().size());
		assertEquals(flights[6], f.getDirectFlights().get(0));
		assertEquals(2, f.getIndirectFlights().size());
		assertTrue((flights[0] == f.getIndirectFlights().get(0)[0] && 
				flights[19] == f.getIndirectFlights().get(0)[1])  || 
				(flights[28] == f.getIndirectFlights().get(0)[0] && 
						flights[27] == f.getIndirectFlights().get(0)[1]));	
		assertTrue((flights[0] == f.getIndirectFlights().get(1)[0] && 
				flights[19] == f.getIndirectFlights().get(1)[1])  || 
				(flights[28] == f.getIndirectFlights().get(1)[0] && 
						flights[27] == f.getIndirectFlights().get(1)[1]));	
		assertEquals(21, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testTwoOptionsIndirectFlightDirectOptionTime() {
		assertEquals(1, f.findFlights(false, "PHL", "ATL", 121));
		assertEquals(1, f.getDirectFlights().size());
		assertEquals(flights[6], f.getDirectFlights().get(0));
		assertTrue(f.getIndirectFlights().isEmpty());
		assertEquals(22, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testTwoOptionsIndirectFlightTwoOutOfThreeTime() {
		assertEquals(2, f.findFlights(false, "PHL", "ATL", 95+140+1));
		//PHL to CDG to ATL takes too long
		assertEquals(1, f.getDirectFlights().size());
		assertEquals(flights[6], f.getDirectFlights().get(0));
		assertEquals(1, f.getIndirectFlights().size());
		assertEquals(flights[0], f.getIndirectFlights().get(0)[0]);
		assertEquals(flights[19], f.getIndirectFlights().get(0)[1]);	
		assertEquals(23, FlightFinder.getNumSearches());
	}
	
	@Test
	public void testTwoOptionsIndirectFlight() {
		assertEquals(2, f.findFlights(false, "LHR", "PHL", 2000));
		assertTrue(f.getDirectFlights().isEmpty());
		assertEquals(2, f.getDirectFlights().size());
		//LHR-BOS-PHL and LHR-JFK-PHL
		assertTrue((flights[25] == f.getIndirectFlights().get(0)[0] && 
				flights[1] == f.getIndirectFlights().get(0)[1])  || 
				(flights[21] == f.getIndirectFlights().get(0)[0] && 
						flights[23] == f.getIndirectFlights().get(0)[1]));	
		assertTrue((flights[25] == f.getIndirectFlights().get(1)[0] && 
				flights[1] == f.getIndirectFlights().get(1)[1])  || 
				(flights[21] == f.getIndirectFlights().get(1)[0] && 
						flights[23] == f.getIndirectFlights().get(1)[1]));	
		assertEquals(24, FlightFinder.getNumSearches());
		
	}
	
}
