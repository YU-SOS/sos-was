package com.se.sos.domain.user.repository;

import com.se.sos.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByProviderUserInfo(String providerUserInfo);
}
