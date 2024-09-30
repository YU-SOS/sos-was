package com.se.sos.domain.reception.repository;

import com.se.sos.domain.reception.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient,UUID> {
}
