package com.howread.jwt;

import com.howread.user.service.dto.AuthClaims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JwtUtilTest {
    @Autowired
    JwtUtil jwtUtil;

    @Test
    void createAccessToken() {
        String token = jwtUtil.createAccessToken(new AuthClaims(1L));
        System.out.println("token = " + token);
    }
}
