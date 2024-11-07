package com.se.sos.domain.user.repository;

import com.se.sos.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByProviderUserInfo(String providerUserInfo);

    long count();
}
