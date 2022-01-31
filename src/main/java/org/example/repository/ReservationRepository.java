package org.example.repository;

import org.example.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByTenant_Id(Long id);

    List<Reservation> findAllByProperty_Id(Long id);

    boolean existsReservationByPropertyIdAndStartLessThanEqualAndEndGreaterThanEqual(Long propertyId, Date date, Date date2);
}
