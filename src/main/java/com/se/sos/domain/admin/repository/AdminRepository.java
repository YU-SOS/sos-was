package com.se.sos.domain.admin.repository;

import com.se.sos.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByAdminId(String adminId);
}
