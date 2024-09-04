package kr.co.swm.config;

import jakarta.servlet.http.Cookie;
import kr.co.swm.config.auth.CustomUserDetailsService;
import kr.co.swm.jwt.filter.JWTFilter;
import kr.co.swm.jwt.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 설정파일
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // 특정 권한에 따라  메서드 호출 제어하기 위해 설정
@RequiredArgsConstructor// 웹사이트 보안 활성화 (모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 어노테이션)
public class SecurityConfig {
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;  // 사용자 정보

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 모든 페이지에 대한 접근권한 설정, 사이트 위변조 방지 해제
        http    //authorizeHttpRequests 어떤 요청에 대해 어떤 권한이 필요한지 설정
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers : 특정 URL 지정
                        // 인증 없이 접근 가능
                                .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/scss/**").permitAll() // 정적 리소스
                                .requestMatchers("/", "/sms/send", "/signin", "/signup", "/idcheck", "/signform", "/lostpass", "/find-userId", "find-password", "/logout-success").permitAll()
                                .requestMatchers("/tour","/hotel-single/**", "/get-list").permitAll()
                                .requestMatchers("/enroll", "/save-location", "/couponList", "/addCoupon", "/add-all").permitAll()
                                .requestMatchers("/static/**", "/web-login", "/accommodationImages/**", "/get-place-items").permitAll()
                                .requestMatchers("/member/mypage", "/mypage", "/update-profile","/update-phone", "/update-password", "/login-log", "/withdraw-account", "/coupons", "/reservation-list").hasRole("USER") // 일반유저 권한을 가진 사용자만 접근 (추후 수정)
                                .requestMatchers("/seller-main.do", "/reservation.do", "/reservation-search.do", "/reservation-daily.do", "/reservation-monthly.do", "/basic-rate-list.do", "/getRoomRates", "/basic-rate-write.do", "/season-period.do", "/extra-delete", "/periods-update", "/reservation-monthly-data").hasRole("ACCOMMODATION_ADMIN")
                                .requestMatchers("/web-center", "/web-coupon", "/web-member", "/web-seller", "/web-manager", "/admin-signup", "/web-coupon-save").hasRole("SITE_ADMIN") // 사이트 관리자 권한을 가진 사용자만 접근
                                // hasRole([role]) : 현재 사용자의 권한이 파라미터의 권한과 동일한 경우 true
                                //.hasAnyRole("ACCOMMODATION_ADMIN", "SITE_ADMIN", "USER")  //여러 권한 허용
                                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                        // .authenticated()는 특정 권한이 아니라, 사용자가 로그인되어 있는지 여부
                )

                // 보안상의 이유로 다른 도메인에서 온 요청 차단
                // CSRF 비활성화 -> 모든 도메인의 요청 허용
                .csrf(AbstractHttpConfigurer::disable)
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .permitAll()
                        .deleteCookies("Authorization")
//                        .addLogoutHandler((request, response, authentication) -> {
//                            Cookie accessCookie = new Cookie("Authorization", null);
//                            accessCookie.setPath("/");
//                            accessCookie.setDomain("localhost");
//                            accessCookie.setHttpOnly(true);
//                            accessCookie.setMaxAge(0);
//                            response.addCookie(accessCookie);
//                        })
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/logout-success");
                        }))


                // 세션 설정, JWT를 통한 인증, 인가 작업을 위해서는 세션을 무상태 (STATELESS) 로 설정하는 것이 중요!
                // 스프링 시큐리티 -> 기본적으로 인증이 필요한 요청에 대해 세션을 생성하고 관리하는 기능을 제공
                // JWT 인증 방식 -> 세션 생성 X
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // JWTFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
        http.addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        System.out.println("스프링 시큐리티 필터 부분 완료");

        return http.build(); // HttpSecurity 객체를 빌드하는 마지막 호출
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(customUserDetailsService) // 사용자 정보 가져오기
                .passwordEncoder(passwordEncoder()); // 비밀번호 암호화하고 검증

        return authBuilder.build(); // AuthenticationManager 객체 생성
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
