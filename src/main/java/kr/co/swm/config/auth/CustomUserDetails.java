package kr.co.swm.config.auth;

import kr.co.swm.member.model.dto.AdminDTO;
import kr.co.swm.member.model.dto.MemberDTO;
import kr.co.swm.member.model.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// 사용자 정보를 스프링 시큐리티가 이해할 수 있는 형식으로 제공 하는 역할
// (사용자 인증, 사용자 권한 관리)
//@RequiredArgsConstructor
public class CustomUserDetails<T extends MemberDTO> implements UserDetails {
    private final T dto;

    public CustomUserDetails(T dto) {
        this.dto = dto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("CustomUserDetails 권한 : " + dto.getRole());
        return List.of(new SimpleGrantedAuthority(dto.getRole()));
    }

    @Override
    public String getPassword() {
        return dto.getUserPwd();
    }

    @Override
    public String getUsername() {
        return dto.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public T getMemberDTO() {
        return dto;
    }
}
