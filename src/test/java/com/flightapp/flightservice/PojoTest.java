package com.flightapp.flightservice;

import com.flightapp.flightservice.dto.FlightResponse;
import com.flightapp.flightservice.dto.FlightSearchRequest;
import com.flightapp.flightservice.dto.InventoryAddRequest;
import com.flightapp.flightservice.entity.Flight;
import com.flightapp.flightservice.entity.FlightInventory;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class PojoTest {

    @Test
    void testAllPojos() {
        testFlight();
        testFlightInventory();
        testFlightSearchRequest();
        testFlightResponse();
        testInventoryAddRequest();
    }

    private void testFlight() {
        Flight f1 = new Flight();
        f1.setFlightId("1");
        f1.setAirlineCode("AI");
        f1.setAirlineName("Air India");
        f1.setSource("A");
        f1.setDestination("B");
        f1.setDepartureTime(LocalDateTime.now());
        f1.setArrivalTime(LocalDateTime.now());
        f1.setPrice(100.0);
        f1.setTotalSeats(100);
        f1.setAvailableSeats(50);
        f1.setAircraft("747");
        f1.setCreatedAt(1L);
        f1.setUpdatedAt(2L);

        assertNotNull(f1.getFlightId());
        assertNotNull(f1.getAirlineCode());
        assertNotNull(f1.getAirlineName());
        assertNotNull(f1.getSource());
        assertNotNull(f1.getDestination());
        assertNotNull(f1.getDepartureTime());
        assertNotNull(f1.getArrivalTime());
        assertNotNull(f1.getPrice());
        assertNotNull(f1.getTotalSeats());
        assertNotNull(f1.getAvailableSeats());
        assertNotNull(f1.getAircraft());
        assertNotNull(f1.getCreatedAt());
        assertNotNull(f1.getUpdatedAt());
        assertNotNull(f1.toString());

        Flight f2 = new Flight("1", "AI", "Air India", "A", "B", f1.getDepartureTime(), f1.getArrivalTime(), 100.0, 100, 50, "747", 1L, 2L);
        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1, new Object());
        assertNotEquals(f1, null);
    }

    private void testFlightInventory() {
        FlightInventory i1 = new FlightInventory();
        i1.setInventoryId("1");
        i1.setFlightId("F1");
        i1.setTotalSeats(100);
        i1.setBookedSeats(10);
        i1.setAvailableSeats(90);
        i1.setCreatedAt(1L);
        i1.setUpdatedAt(2L);

        assertNotNull(i1.getInventoryId());
        assertNotNull(i1.getFlightId());
        assertNotNull(i1.getTotalSeats());
        assertNotNull(i1.getBookedSeats());
        assertNotNull(i1.getAvailableSeats());
        assertNotNull(i1.getCreatedAt());
        assertNotNull(i1.getUpdatedAt());
        assertNotNull(i1.toString());

        FlightInventory i2 = new FlightInventory("1", "F1", 100, 10, 90, 1L, 2L);
        assertEquals(i1, i2);
        assertEquals(i1.hashCode(), i2.hashCode());
        assertNotEquals(i1, new Object());
    }

    private void testFlightSearchRequest() {
        FlightSearchRequest r1 = new FlightSearchRequest();
        r1.setSource("A");
        r1.setDestination("B");
        r1.setDepartureDate(LocalDate.now());
        r1.setJourneyType("ONE_WAY");

        assertNotNull(r1.getSource());
        assertNotNull(r1.getDestination());
        assertNotNull(r1.getDepartureDate());
        assertNotNull(r1.getJourneyType());
        assertNotNull(r1.toString());

        FlightSearchRequest r2 = new FlightSearchRequest("A", "B", LocalDate.now(), "ONE_WAY");
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    private void testFlightResponse() {
        FlightResponse r1 = new FlightResponse();
        r1.setFlightId("F1");
        r1.setAirlineCode("AI");
        r1.setAirlineName("Air India");
        r1.setSource("A");
        r1.setDestination("B");
        r1.setDepartureTime(LocalDateTime.now());
        r1.setArrivalTime(LocalDateTime.now());
        r1.setPrice(100.0);
        r1.setAvailableSeats(10);
        r1.setAircraft("747");

        assertNotNull(r1.toString());
        FlightResponse r2 = new FlightResponse("F1", "AI", "Air India", "A", "B", r1.getDepartureTime(), r1.getArrivalTime(), 100.0, 10, "747");
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    private void testInventoryAddRequest() {
        InventoryAddRequest r1 = new InventoryAddRequest();
        r1.setAirlineCode("AI");
        r1.setAirlineName("Test");
        r1.setSource("A");
        r1.setDestination("B");
        r1.setDepartureTime("10:00");
        r1.setArrivalTime("11:00");
        r1.setPrice(100.0);
        r1.setTotalSeats(100);
        r1.setAircraft("747");

        assertNotNull(r1.toString());
        InventoryAddRequest r2 = new InventoryAddRequest("AI", "Test", "A", "B", "10:00", "11:00", 100.0, 100, "747");
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }
}