package com.se.sos.domain.ambulance.repository;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AmbulanceRepository extends JpaRepository<Ambulance, UUID> {

    Optional<Ambulance> findByIdAndRole(UUID id, Role role);

    Optional<Ambulance> findByAmbulanceId(String ambulanceId);

    List<Ambulance> findByRole(Role role);

    boolean existsById(UUID id);

    boolean existsByAmbulanceId(String ambulanceId);

    boolean existsByName(String name);

    long countByRole(Role role);
}
