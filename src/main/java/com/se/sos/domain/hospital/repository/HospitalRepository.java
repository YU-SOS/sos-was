package com.se.sos.domain.hospital.repository;

import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HospitalRepository extends JpaRepository<Hospital, UUID> {
    Optional<Hospital> findById(UUID id);
    Optional<Hospital> findByHospitalId(String hospitalId);
    Optional<Hospital> findByName(String name);
    List<Hospital> findByRole(Role role);

    boolean existsByHospitalId(String hospitalId);

    boolean existsByName(String name);
}
