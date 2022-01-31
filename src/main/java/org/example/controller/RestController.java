package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.dto.DataDto;
import org.example.model.Reservation;
import org.example.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@RequestMapping()
public class RestController {

    private final ReservationService reservationService;

    @PostMapping("/createReservation")
    public ResponseEntity<String> createReservation(@RequestBody DataDto request) {
        Reservation reservation = reservationService.createReservation(request);
        if (reservation == null) {
            return ResponseEntity.status(406).body("The given date is already booked");
        } else return ResponseEntity.ok()
                .body(reservation.toString());
    }

    @PostMapping("/changeReservation")
    public ResponseEntity<String> changeReservation(@RequestParam Long id, @RequestBody DataDto request) {
        if (reservationService.changeReservation(id, request)) {
            return ResponseEntity.ok()
                    .body("Reservation has been changed");
        } else return ResponseEntity.notFound().build();

    }

    @GetMapping("/getReservationForTenant")
    public ResponseEntity<List<Reservation>> getReservationForTenant(@RequestParam String tenantName) {
        return ResponseEntity.ok()
                .body(reservationService.getReservationForTenant(tenantName));
    }

    @GetMapping("/getReservationForProperty")
    public ResponseEntity<List<Reservation>> getReservationForProperty(@RequestParam String propertyName) {
        return ResponseEntity.ok()
                .body(reservationService.getReservationForProperty(propertyName));
    }
}
