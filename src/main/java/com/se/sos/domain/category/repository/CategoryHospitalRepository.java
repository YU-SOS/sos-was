package com.se.sos.domain.category.repository;

import com.se.sos.domain.category.entity.Category;
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
    @Query("SELECT ch.hospital FROM CategoryHospital ch " +
            "WHERE ch.category IN :categories " +
            "GROUP BY ch.hospital " +
            "HAVING COUNT(DISTINCT ch.category) = :categorySize")
    Page<Hospital> findHospitalsByCategories(@Param("categories") List<Category> categories,
                                             @Param("categorySize") int categorySize,
                                             Pageable pageable);

}
