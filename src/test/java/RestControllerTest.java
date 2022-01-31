import org.example.controller.RestController;
import org.example.controller.dto.DataDto;
import org.example.model.Reservation;
import org.example.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestControllerTest {

    private ReservationService reservationService;
    private RestController restController;

    @BeforeEach
    public void setup() {
        reservationService  = mock(ReservationService.class);
        restController = new RestController(reservationService);
    }

    @Test
    void createReservation_shouldReturnStatusOK() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DataDto dataDto = new DataDto(format.parse("2022-01-01"), format.parse("2022-01-30"), "Steve Kane", "Merton");

        when(reservationService.createReservation(dataDto)).thenReturn(new Reservation());
        ResponseEntity<String> actual = restController.createReservation(dataDto);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    void createReservation_shouldReturnStatusNOT_ACCEPTABLE() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DataDto dataDto = new DataDto(format.parse("2022-01-01"), format.parse("2022-01-30"), "Steve Kane", "Merton");

        when(reservationService.createReservation(dataDto)).thenReturn(null);
        ResponseEntity<String> actual = restController.createReservation(dataDto);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, actual.getStatusCode());
    }

}
