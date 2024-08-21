package kr.co.swm.config.auth;

import kr.co.swm.member.model.dto.MemberDTO;
import kr.co.swm.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByUsername(String userId, String signRole) throws UsernameNotFoundException {
        MemberDTO member = null;
        // 사이트 관리자
        if ("ROLE_SITE_ADMIN".equals(signRole)) {
            member = memberMapper.findBySiteAdminInfo(userId);
            log.info("CustomUserDetailsService 사이트 관리자 권한 : {} ", member);
        }// 업소관리자
        else if ("ROLE_ACCOMMODATION_ADMIN".equals(signRole)) {
            member = memberMapper.findByAccommAdminInfo(userId);
            log.info("CustomUserDetailsService 업소관리자 권한 : {} ", member);
        } else if ("ROLE_USER".equals(signRole)) {
            // 일반 사용자
            member = memberMapper.findByUserInfo(userId);
            log.info("CustomUserDetailsService 일반유저 권한 : {} ", member);
        } else {
            // 숙소 관리자도 아닐 경우 예외 발생
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId);
        }
        return new CustomUserDetails(member);
    }
}