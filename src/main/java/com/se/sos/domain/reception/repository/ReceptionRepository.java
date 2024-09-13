package com.se.sos.domain.reception.repository;

import com.se.sos.domain.reception.entity.Reception;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReceptionRepository extends JpaRepository<Reception, UUID> {
}
