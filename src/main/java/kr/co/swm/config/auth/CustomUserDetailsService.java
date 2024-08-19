package kr.co.swm.config.auth;

import kr.co.swm.member.model.dto.MemberDTO;
import kr.co.swm.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberMapper memberMapper;


    @Override
    // 일반유저 & 사이트 관리자
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 일반 사용자 및 사이트 관리자 정보
        MemberDTO member = memberMapper.findByUserId(userId);
        System.out.println("------CustomUserDetailsService findByUserId 권한-------------");

        // 일반 유저가 아닌 경우, 숙소 관리자 정보 로드
        if (member == null) {
            member = memberMapper.accommodationAdminByUserId(userId);
            System.out.println("------CustomUserDetailsService accommodationAdminByUserId 권한------");

            if (member == null){
                // 숙소 관리자도 아닐 경우 예외 발생
                throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId);
            }
        }
        return new CustomUserDetails(member);
    }





}
