package kr.co.swm.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import static io.jsonwebtoken.Jwts.*;

@Slf4j
@Component
// JWT의 생성 및 검증 처리
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    // 비밀키 객체 생성
    private Key getKey() {
        // base64 인코딩을 하지않고, 키를 직접 바이트 배열로 변환
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // JWT 생성 메서드
    public String create(Map<String, Object> claims, LocalDateTime expireAt, Long accommAdminKey, Long userNo) {
        // 임의로 만든 암호키로 key 설정
        var key = getKey();
        // token의 Expire(만기) 시간을 객체로 변환
        var _expireAt = Date.from(expireAt.atZone(ZoneId.systemDefault()).toInstant());

        // 업소 관리자 키가 있는 경우에만 추가
        if (accommAdminKey != null) {
            claims.put("accommAdminKey", accommAdminKey);
        }

        // 클레임에 userNo를 추가
        if (userNo != null) {
            claims.put("userNo", userNo); // 클레임에 추가
        }

        // JWT 생성 및 로그 출력
        String token = Jwts.builder()
                .setClaims(claims) // 사용자 정보 설정
                .setExpiration(_expireAt) //토큰의 만료 시간을 설정
                .signWith(key, SignatureAlgorithm.HS256) // 서명 방식과 키 설정
                .compact(); //JWT를 문자열로 반환

        log.info("토큰생성 : {}", token);
        return token;
    }

    // 토큰의 만료 여부를 확인하는 메서드
    public Boolean isExpired(String token) {
        try {
            // 서명 검증 및 토큰 파싱
            Claims claims = Jwts.parser()
                    .setSigningKey(getKey())  // 서명 검증에 사용할 키 설정
                    .build()
                    .parseClaimsJws(token)  // 토큰 파싱
                    .getBody();  // 클레임 반환

            // 만료 시간과 현재 시간 비교
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우 true 반환
            return true;
        } catch (Exception e) {
            // 기타 예외 발생 시 false 반환
            return false;
        }
    }

    // JWT에서 사용자 ID를 추출
    public String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("userId", String.class);
        } catch (Exception e) {
            log.error("JWT에서 사용자 ID를 추출하는 중 오류 발생: " + e.getMessage());
            return null;
        }
    }

    // JWT에서 userNo 추출
    public Long getUserNoFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("userNo", Long.class);
        } catch (Exception e) {
            log.error("JWT에서 사용자 키를 추출하는 중 오류 발생: " + e.getMessage());
            return null;
        }
    }

    // JWT에서 role을 추출
    public String getRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("role", String.class);
        } catch (Exception e) {
            log.error("JWT에서 권한을 추출하는 중 오류 발생: " + e.getMessage());
            return null;
        }
    }

    // JWT에서 업소관리자 키 추출
    public Long getAccommAdminKeyFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("accommAdminKey", Long.class);
        } catch (Exception e) {
            log.error("JWT에서 업소 관리자 키를 추출하는 중 오류 발생: " + e.getMessage());
            return null;
        }
    }


    // JWT 유효성 검사
    public void validate(String token) {
        var key = getKey();

        try {
            var result = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token); // 토큰 파싱

            result.getPayload().forEach((key1, value1) -> log.info("key : {}, value : {}", key1, value1));
        } catch (Exception e) {
            if (e instanceof SignatureException) { // 토큰의 signature가 유효하지 않을 때
                throw new RuntimeException("JWT Token 유효하지 않음");
            } else if (e instanceof ExpiredJwtException) { // 토큰 만료
                throw new RuntimeException("JWT Token 기간 만료");
            } else { // 그 외 에러
                throw new RuntimeException("JWT 에러");
            }
        }

    }
}
