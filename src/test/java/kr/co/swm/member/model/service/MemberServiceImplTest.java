package kr.co.swm.member.model.service;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.swm.config.auth.CustomUserDetails;
import kr.co.swm.config.auth.CustomUserDetailsService;
import kr.co.swm.jwt.util.JWTUtil;
import kr.co.swm.member.model.dto.MemberDTO;
import kr.co.swm.member.model.dto.UserDTO;
import kr.co.swm.member.model.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class MemberServiceImplTest {

    // Mock 객체(가짜 객체)를 정의
    @Mock
    private MemberMapper memberMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private JWTUtil jwtUtil;

    // 테스트할 Service 객체 (실제 테스트 대상)
    @InjectMocks
    private MemberServiceImpl memberServiceImpl;

    /*
    * 각 테스트 메서드가 실행되기 전에 호출되는 설정 메세드
    *  Mockito를 사용하여 테스트에 필요한 Mock 객체와 MemberServiceImpl 인스턴스를 초기화
    * */
    @BeforeEach
    void beforeEach() {
        // Mockito 초기화: @Mock으로 정의된 객체들을 초기화
        MockitoAnnotations.openMocks(this);
    }
    /*
    * Given : 테스트에 필요한 초기 상태 설정
    * When : 테스트할 메서드 호출
    * Then : 결과 검증
    * */

    @Test //회원가입 테스트
    void setSignup() {
        // given : 테스트 데이터 준비
        UserDTO userDTO = new UserDTO();
        userDTO.setUserPwd("qwer1234!"); // 사용자 비밀번호
        userDTO.setConfirmPassword("qwer1234!"); // 비밀번호 확인

        // Mock 객체 설정
        when(passwordEncoder.encode(anyString())).thenReturn("encodePassword"); // 비밀번호 암호화 시 "encodedPassword" 반환
        when(memberMapper.setSignUp(any(UserDTO.class))).thenReturn(1); // 회원가입 성공 -> 1

        // when : 메서드 호출
        int result = memberServiceImpl.setSignup(userDTO);

        // then : 결과 검증
        assertThat(result).isEqualTo(1); // 결과가 1 -> 성공
        verify(passwordEncoder, times(1)).encode("qwer1234!"); // passwordEncoder의 encode 메서드가 "qwer1234!" 문자열로 한 번 호출되었는지 검증
        verify(memberMapper, times(1)).setSignUp(userDTO); // 회원가입 메서드가 한 번 호출되었는지 검증
    }

    @Test // id 중복 검사 테스트
    void idCheck() {
        // given : 테스트 데이터 id 존재하는 경우 설정
        String userId = "user";
        when(memberMapper.idCheck(userId)).thenReturn(1); // id 존재 -> 1

        // when : 테스트 메서드 호출
        int result = memberServiceImpl.idCheck(userId);

        // then : 결과 검증
        assertThat(result).isEqualTo(1); // 결과 1 -> id 중복
        verify(memberMapper).idCheck(userId); // id 중복 검사 메서드 호출 검증
    }
}