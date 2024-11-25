package com.se.sos.domain.reception.repository;

import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.ReceptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReceptionRepository extends JpaRepository<Reception, UUID> {
    Page<Reception> findByHospital_Id(UUID hospitalId, Pageable pageable);

    Optional<Reception> findReceptionById(UUID receptionId);
    Optional<Reception> findByNumber(String number);

    List<Reception> findByHospital_IdAndReceptionStatusIn(UUID hospitalId, List<ReceptionStatus> receptionStatuses);
    boolean existsByNumber(String number);

    long count();
}
