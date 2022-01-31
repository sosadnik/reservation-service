package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    public Reservation(Date start, Date end, org.example.model.Person tenant, org.example.model.Property property, double cost) {
        this.start = start;
        this.end = end;
        this.tenant = tenant;
        this.property = property;
        this.cost = cost;
    }

    @Id
    @SequenceGenerator(
            name = "reservation_id_sequence",
            sequenceName = "reservation_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_id_sequence"
    )
    private Long id;
    private Date start;
    private Date end;
    private double cost;
    @OneToOne
    @JoinColumn(name = "tenant_id")
    private org.example.model.Person tenant;
    @OneToOne
    @JoinColumn(name = "property_id")
    private org.example.model.Property property;

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", cost=" + cost +
                ", tenant=" + tenant +
                ", property=" + property +
                '}';
    }
}
