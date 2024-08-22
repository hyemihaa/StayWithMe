package kr.co.swm.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.co.swm.member.validator.PasswordMatches;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@PasswordMatches
public class UserDTO extends MemberDTO{
    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String confirmPassword;

    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Pattern(regexp = "^[가-힣]{2,10}$", message = "한글만 가능 합니다.")
    private String userName;

    @NotBlank(message = "전화번호는 필수 입력값입니다")
    @Pattern(regexp = "^010\\d{8}$", message = "'-' 없이 입력하세요.")
    private String userPhone;

    private LocalDateTime createdDate; // 생성일
    private LocalDateTime deletedDate; // 탈퇴일


}
