package kr.co.swm.jwt;

import kr.co.swm.jwt.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class JWTUtilTest {
    @Autowired
    JWTUtil jwtUtil;

    @Test
    void create() {
        // given
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", "testUser");
        claims.put("role", "USER");
        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10);

        // when
        String token = jwtUtil.create(claims, expireAt);

        // then
        assertNotNull(token);
        log.info("Generated token: {}", token);
    }

    @Test
    void isExpired() {
        // given
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", "testUser");
        claims.put("role", "USER");
        LocalDateTime expireAt = LocalDateTime.now().minusMinutes(10); // 이미 만료된 시간

        String expiredToken = jwtUtil.create(claims, expireAt);

        // when
        Boolean isExpired = jwtUtil.isExpired(expiredToken);

        // then
        assertTrue(isExpired);
        log.info("isExpired : {}", isExpired);
    }

    @Test
    void validate() {
        // given
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", "testUser");
        claims.put("role", "USER");
        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10);

        String token = jwtUtil.create(claims, expireAt);

        // when & then
        assertDoesNotThrow(() -> jwtUtil.validate(token));
    }

    @Test
    void getUserIdFromToken() {
        // given
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", "testUser");
        claims.put("role", "USER");
        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10);

        String token = jwtUtil.create(claims, expireAt);

        // when
        String userId = jwtUtil.getUserIdFromToken(token);

        // then
        assertEquals("testUser", userId);
    }

    @Test
    void getRoleFromToken() {
        // given
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", "testUser");
        claims.put("role", "USER");
        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10);

        String token = jwtUtil.create(claims, expireAt);

        // when
        String role = jwtUtil.getRoleFromToken(token);

        // then
        assertEquals("USER", role);
    }
}