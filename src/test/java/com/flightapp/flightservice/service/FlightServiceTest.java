package com.flightapp.flightservice.service;

import com.flightapp.flightservice.dto.FlightResponse;
import com.flightapp.flightservice.dto.FlightSearchRequest;
import com.flightapp.flightservice.dto.InventoryAddRequest;
import com.flightapp.flightservice.entity.Flight;
import com.flightapp.flightservice.entity.FlightInventory;
import com.flightapp.flightservice.repository.FlightInventoryRepository;
import com.flightapp.flightservice.repository.FlightRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private FlightInventoryRepository inventoryRepository;

    @InjectMocks
    private FlightService flightService;

    @Test
    void testAddFlightInventory() {
        InventoryAddRequest req = new InventoryAddRequest("AI", "Air India", "DEL", "BOM", "2023-01-01T10:00:00", "2023-01-01T12:00:00", 100.0, 100, "737");
        Flight savedFlight = new Flight();
        savedFlight.setFlightId("F1");
        
        when(flightRepository.save(any(Flight.class))).thenReturn(savedFlight);
        when(inventoryRepository.save(any(FlightInventory.class))).thenReturn(new FlightInventory());

        FlightResponse res = flightService.addFlightInventory(req);
        Assertions.assertEquals("F1", res.getFlightId());
    }

    @Test
    void testSearchFlights() {
        FlightSearchRequest req = new FlightSearchRequest("DEL", "BOM", LocalDate.now(), "ONE_WAY");
        when(flightRepository.findBySourceAndDestinationAndDepartureTimeBetween(any(), any(), any(), any()))
                .thenReturn(List.of(new Flight()));
        
        List<FlightResponse> res = flightService.searchFlights(req);
        Assertions.assertEquals(1, res.size());
    }
    
    @Test
    void testGetFlightById() {
        when(flightRepository.findById("F1")).thenReturn(Optional.of(new Flight()));
        Assertions.assertTrue(flightService.getFlightById("F1").isPresent());
    }

    @Test
    void testUpdateSeats_Success() {
        Flight f = new Flight();
        f.setFlightId("F1");
        f.setAvailableSeats(10);
        
        FlightInventory inv = new FlightInventory();
        inv.setBookedSeats(0);
        inv.setAvailableSeats(10);

        when(flightRepository.findById("F1")).thenReturn(Optional.of(f));
        when(inventoryRepository.findByFlightId("F1")).thenReturn(Optional.of(inv));

        flightService.updateAvailableSeats("F1", 2);

        Assertions.assertEquals(8, f.getAvailableSeats());
        verify(flightRepository).save(f);
        verify(inventoryRepository).save(inv);
    }

    @Test
    void testUpdateSeats_NotFound() {
        when(flightRepository.findById("INVALID")).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class, () -> flightService.updateAvailableSeats("INVALID", 1));
    }

    @Test
    void testUpdateSeats_NotEnough() {
        Flight f = new Flight();
        f.setAvailableSeats(1);
        when(flightRepository.findById("F1")).thenReturn(Optional.of(f));
        
        Assertions.assertThrows(RuntimeException.class, () -> flightService.updateAvailableSeats("F1", 2));
    }

    @Test
    void testUpdateSeats_InventoryMissing() {
        Flight f = new Flight();
        f.setAvailableSeats(10);
        when(flightRepository.findById("F1")).thenReturn(Optional.of(f));
        when(inventoryRepository.findByFlightId("F1")).thenReturn(Optional.empty());

        flightService.updateAvailableSeats("F1", 1);
        verify(inventoryRepository, never()).save(any());
    }

    @Test
    void testReleaseSeats_Success() {
        Flight f = new Flight();
        f.setFlightId("F1");
        f.setAvailableSeats(5);
        
        FlightInventory inv = new FlightInventory();
        inv.setBookedSeats(5);
        inv.setAvailableSeats(5);

        when(flightRepository.findById("F1")).thenReturn(Optional.of(f));
        when(inventoryRepository.findByFlightId("F1")).thenReturn(Optional.of(inv));

        flightService.releaseSeats("F1", 2);

        Assertions.assertEquals(7, f.getAvailableSeats());
        verify(flightRepository).save(f);
        verify(inventoryRepository).save(inv);
    }
    
    @Test
    void testReleaseSeats_NotFound() {
        when(flightRepository.findById("INVALID")).thenReturn(Optional.empty());
        Assertions.assertThrows(RuntimeException.class, () -> flightService.releaseSeats("INVALID", 1));
    }
    
    @Test
    void testReleaseSeats_InventoryMissing() {
        Flight f = new Flight();
        f.setAvailableSeats(5);
        when(flightRepository.findById("F1")).thenReturn(Optional.of(f));
        when(inventoryRepository.findByFlightId("F1")).thenReturn(Optional.empty());
        
        flightService.releaseSeats("F1", 1);
        verify(inventoryRepository, never()).save(any());
    }
}