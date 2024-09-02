package com.se.sos.domain.ambulance.repository;

import com.se.sos.domain.ambulance.entity.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AmbulanceRepository extends JpaRepository<Ambulance, UUID> {
//    Optional<Ambulance> findByAmbulanceId(String ambulanceId);
    Ambulance findByAmbulanceId(String ambulanceId);
}
