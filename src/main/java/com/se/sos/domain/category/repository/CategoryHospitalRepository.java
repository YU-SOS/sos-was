package com.se.sos.domain.category.repository;

import com.se.sos.domain.category.entity.CategoryHospital;
import com.se.sos.domain.hospital.entity.Hospital;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CategoryHospitalRepository extends JpaRepository<CategoryHospital, UUID> {
    @Query("select distinct ch.hospital from CategoryHospital ch where ch.category.id in (:categoryIds)")
    Page<Hospital> findHospitalsByCategories(@Param("categoryIds") List<Long> categoryIds , Pageable pageable);
}
