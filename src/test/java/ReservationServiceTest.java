import org.example.controller.dto.DataDto;
import org.example.model.Person;
import org.example.model.Property;
import org.example.model.Reservation;
import org.example.repository.PersonRepository;
import org.example.repository.PropertyRepository;
import org.example.repository.ReservationRepository;
import org.example.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservationServiceTest {
    private ReservationService reservationService;
    private PersonRepository personRepository;
    private PropertyRepository propertyRepository;
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setup() {
        personRepository = mock(PersonRepository.class);
        propertyRepository = mock(PropertyRepository.class);
        reservationRepository = mock(ReservationRepository.class);
        reservationService = new ReservationService(personRepository, propertyRepository, reservationRepository);
    }

    @Test
    void createReservation_shouldReturnReservation() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DataDto dataDto = new DataDto(format.parse("2022-01-01"), format.parse("2022-01-30"), "Steve Kane", "Merton");
        Reservation expected = new Reservation(1L,
                format.parse("2022-01-01"),
                format.parse("2022-01-30"),
                3600.0,
                new Person(3L, "Steve Kane"),
                new Property(1L, "Merton", 120, 52.5, new Person(1L, "Dan Vega"), "Nice place"));

        when(reservationRepository.existsReservationByPropertyIdAndStartLessThanEqualAndEndGreaterThanEqual(1L, format.parse("2022-01-01"), format.parse("2022-01-01"))).thenReturn(false);
        when(reservationRepository.existsReservationByPropertyIdAndStartLessThanEqualAndEndGreaterThanEqual(1L, format.parse("2022-01-30"), format.parse("2022-01-30"))).thenReturn(false);
        when(reservationRepository.saveAndFlush(any(Reservation.class))).thenReturn(expected);
        when(propertyRepository.findPropertyByName(anyString())).thenReturn(new Property(1L, "Merton", 120, 52.5, new Person(1L, "Dan Vega"), "Nice place"));
        Reservation actual = reservationService.createReservation(dataDto);

        assertEquals(expected, actual);
    }

    @Test
    void createReservation_shouldReturnNull() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DataDto dataDto = new DataDto(format.parse("2022-01-01"), format.parse("2022-01-30"), "Steve Kane", "Merton");

        when(reservationRepository.existsReservationByPropertyIdAndStartLessThanEqualAndEndGreaterThanEqual(1L, format.parse("2022-01-01"), format.parse("2022-01-01"))).thenReturn(true);
        when(reservationRepository.existsReservationByPropertyIdAndStartLessThanEqualAndEndGreaterThanEqual(1L, format.parse("2022-01-30"), format.parse("2022-01-30"))).thenReturn(false);
        when(propertyRepository.findPropertyByName(anyString())).thenReturn(new Property(1L, "Merton", 120, 52.5, new Person(1L, "Dan Vega"), "Nice place"));
        Reservation actual = reservationService.createReservation(dataDto);

        assertNull(actual);
    }
}
