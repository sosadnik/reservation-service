package org.example.services;

import lombok.AllArgsConstructor;
import org.example.controller.dto.DataDto;
import org.example.model.Property;
import org.example.model.Reservation;
import org.example.repository.PersonRepository;
import org.example.repository.PropertyRepository;
import org.example.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReservationService {

    private final PersonRepository personRepository;
    private final PropertyRepository propertyRepository;
    private final ReservationRepository reservationRepository;

    public Reservation createReservation(DataDto request) {
        if (checkBookingTime(request)) {
            return reservationRepository.saveAndFlush(mappingReservation(request));
        } else return null;
    }

    public boolean changeReservation(Long id, DataDto request) {
        if (reservationRepository.findById(id).isPresent()) {
                Reservation reservation = mappingReservation(request);
                reservation.setId(id);
                reservationRepository.saveAndFlush(reservation);
                return true;
        } else return false;
    }

    public List<Reservation> getReservationForTenant(String name) {
        Long personId = personRepository.findPersonByName(name).getId();
        return reservationRepository.findAllByTenant_Id(personId);
    }

    public List<Reservation> getReservationForProperty(String propertyName) {
        Long propertyId = propertyRepository.findPropertyByName(propertyName).getId();
        return reservationRepository.findAllByProperty_Id(propertyId);
    }

    private boolean checkBookingTime(DataDto request) {
        return !reservationRepository.existsReservationByPropertyIdAndStartLessThanEqualAndEndGreaterThanEqual(
                propertyRepository.findPropertyByName(request.getPropertyName()).getId(),
                request.getStart(), request.getStart()) &&
                !reservationRepository.existsReservationByPropertyIdAndStartLessThanEqualAndEndGreaterThanEqual(
                        propertyRepository.findPropertyByName(request.getPropertyName()).getId(),
                        request.getEnd(),
                        request.getEnd());
    }

    private Reservation mappingReservation(DataDto request) {
        Property property = propertyRepository.findPropertyByName(request.getPropertyName());
        return new Reservation(request.getStart(),
                request.getEnd(),
                personRepository.findPersonByName(request.getTenantName()),
                property,
                property.getUnitPrice() * request.getDurationOfBooking());
    }
}
