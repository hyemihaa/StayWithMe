//package kr.co.swm.jwt;
//
//import kr.co.swm.jwt.util.JWTUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Slf4j
//class JWTUtilTest {
//    @Autowired
//    JWTUtil jwtUtil;
//
//    @Test
//    void create() { // 토큰 생성
//        // given : 테스트 상황 설정
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", "testUser");
//        claims.put("role", "USER");
//        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10);
//        Long accommAdminKey = 1L; // 테스트용 관리자 키 설정
//        Long userNo = 1L;
//
//        // when : 테스트가 실행되는 동작 부분
//        String token = jwtUtil.create(claims, expireAt, accommAdminKey, userNo);
//
//        // then : 테스트의 예상 결과를 확인
//        assertNotNull(token);
////        log.info("Generated token: {}", token);
//    }
//
//    @Test
//    void isExpired() {
//        // given
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", "testUser");
//        claims.put("role", "USER");
//        LocalDateTime expireAt = LocalDateTime.now().minusMinutes(10); // 이미 만료된 시간으로 설정
//                                // --> 테스트가 성공하려면 jwtUtil.isExpired() 메서드가 토큰이 만료되었다고 판단
//        Long accommAdminKey = 1L; // 테스트용 관리자 키 설정
//        Long userNo = 1L;
//
//        String expiredToken = jwtUtil.create(claims, expireAt, accommAdminKey, userNo); //토큰을 생성  (이미 만료된 시간이 들어간 토큰)
//
//        // when
//        Boolean isExpired = jwtUtil.isExpired(expiredToken); // 만료된 토큰이기 때문에 true
//
//        // then
//        assertTrue(isExpired);
////        log.info("isExpired : {}", isExpired);
//    }
//
//    @Test
//    void validate() {
//        // given: 유효한 토큰인지 테스트
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", "testUser");
//        claims.put("role", "USER");
//        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10);
//        Long accommAdminKey = 1L; // 테스트용 관리자 키 설정
//        Long userNo = 1L;
//
//        String token = jwtUtil.create(claims, expireAt, accommAdminKey, userNo);  // 유효한 토큰 생성
//
//        // when & then : 토큰이 유효한지 검증
//        assertDoesNotThrow(() -> jwtUtil.validate(token));
//    }
//
//    @Test
//    void getUserIdFromToken() {
//        // given
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", "testUser");
//        claims.put("role", "USER");
//        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10);
//        Long accommAdminKey = 1L; // 테스트용 관리자 키 설정
//        Long userNo = 1L;
//
//        String token = jwtUtil.create(claims, expireAt, accommAdminKey, userNo);
//
//        // when
//        String userId = jwtUtil.getUserIdFromToken(token);
//
//        // then
//        assertEquals("testUser", userId);
//    }
//
//    @Test
//    void getRoleFromToken() {
//        // given
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", "testUser");
//        claims.put("role", "USER");
//        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10);
//        Long accommAdminKey = 1L; // 테스트용 관리자 키 설정
//        Long userNo = 1L;
//
//        String token = jwtUtil.create(claims, expireAt, accommAdminKey, userNo);
//
//        // when
//        String role = jwtUtil.getRoleFromToken(token);
//
//        // then
//        assertEquals("USER", role);
//    }
//
//    @Test
//    void getAccommAdminKeyFromToken() {
//        // given
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", "testUser");
//        claims.put("role", "USER");
//        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10);
//        Long accommAdminKey = 1L; // 테스트용 관리자 키 설정
//        Long userNo = 1L;
//
//        String token = jwtUtil.create(claims, expireAt, accommAdminKey, userNo);
//
//        // when
//        Long extractedAdminKey = jwtUtil.getAccommAdminKeyFromToken(token);
//
//        // then
//        assertEquals(accommAdminKey, extractedAdminKey);
//    }
//}