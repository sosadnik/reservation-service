package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @SequenceGenerator(
            name = "property_id_sequence",
            sequenceName = "property_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "property_id_sequence"
    )
    private Long id;
    private String name;
    private int unitPrice;
    private double area;
    @OneToOne
    @JoinColumn(name = "owner_id")
    private org.example.model.Person owner;
    private String description;

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", area=" + area +
                ", owner=" + owner +
                ", description='" + description + '\'' +
                '}';
    }
}
