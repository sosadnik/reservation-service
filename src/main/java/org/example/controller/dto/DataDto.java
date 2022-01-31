package org.example.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.Duration;
import java.util.Date;

@Getter
@AllArgsConstructor
public class DataDto {

    private Date start;
    private Date end;
    private String tenantName;
    private String propertyName;

    public long getDurationOfBooking() {
        Duration duration = Duration.between(this.start.toInstant(), this.end.toInstant());
        return duration.toDays();
    }
}
