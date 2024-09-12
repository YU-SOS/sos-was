package com.se.sos.domain.user.service;

import com.se.sos.domain.user.dto.LogoutUserReq;
import com.se.sos.domain.user.entity.User;
import com.se.sos.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public LogoutUserReq read(UUID uuid) {
        User user = userRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("user not found"));
        return LogoutUserReq.fromEntity(user);
    }
}
