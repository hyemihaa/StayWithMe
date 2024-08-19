package kr.co.swm.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.swm.config.auth.CustomUserDetails;
import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.member.model.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 모든 요청에 대해 JWT의 유효성을 검사하는 필터
@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 쿠키에서 JWT 토큰을 찾음
        Cookie[] cookies = request.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    token = cookie.getValue();
                    log.info("JWTFilter: 쿠키에서 토큰 찾음 {}", token);
                    break;
                }
            }
        }
        // JWT 토큰이 없거나 유효하지 않은 경우
        if (token == null) {
            log.warn("JWTFilter: JWT 토큰이 null입니다.");
            filterChain.doFilter(request, response);
            return;
        }

        if (jwtUtil.isExpired(token)) {
            log.warn("JWTFilter: JWT 토큰이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

            // 유효한 토큰인 경우 사용자 정보 추출
            String loginId = jwtUtil.getUserIdFromToken(token); // 사용자 ID 추출
            String role = jwtUtil.getRoleFromToken(token); // 사용자 권한 추출

            // DTO 생성 및 설정
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setUserId(loginId);
            memberDTO.setRole(role);

            // CustomUserDetails에 사용자 정보 설정
            CustomUserDetails customUserDetails = new CustomUserDetails(memberDTO);

            log.info("JWTFilter: JWT 토큰 유효! 사용자 이름: {}", customUserDetails.getUsername());
            log.info("JWTFilter: JWT 토큰 유효! 사용자 권한: {}", customUserDetails.getAuthorities());

            // 스프링 시큐리티 인증 토큰 생성
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    customUserDetails, // 사용자 정보
                    null, // 비밀번호는 null로 설정
                    customUserDetails.getAuthorities() // 권한 설정
            );

            // SecurityContext에 인증 정보 설정
            //SecurityContextHolder에 세션을 생성한다. (이 세션은 STATLESS 상태로 관리되기 때문에 해당 요청이 끝나면 소멸
            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("JWTFilter: 인증 정보가 SecurityContext에 설정되었습니다.");

            // 유효한 토큰인 경우, 이후 처리 진행
            filterChain.doFilter(request, response);

        }
    }





