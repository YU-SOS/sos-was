package com.se.sos.domain.paramedic.repository;

import com.se.sos.domain.paramedic.entity.Paramedic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ParamedicRepository extends JpaRepository<Paramedic, UUID> {
    Optional<Paramedic> findByIdAndAmbulanceId(UUID id, UUID ambulanceId);

    void deleteByIdAndAmbulanceId(UUID id, UUID ambulanceId);
}
