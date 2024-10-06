package com.se.sos.domain.reception.repository;

import com.se.sos.domain.reception.entity.Reception;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReceptionRepository extends JpaRepository<Reception, UUID> {
    Page<Reception> findByHospital_Id(UUID hospitalId, Pageable pageable);

    Optional<Reception> findReceptionById(UUID receptionId);
}
