package com.rebook.jwt.service;

import com.rebook.jwt.JwtUtil;
import com.rebook.user.domain.UserEntity;
import com.rebook.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public UserEntity getLoggedInUser(String token) {

        Long subjectId = jwtUtil.extractSubjectId(token);
        UserEntity user = userRepository.findById(subjectId).orElseThrow();
        return user;
    }


}
