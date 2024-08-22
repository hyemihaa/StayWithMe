package kr.co.swm.member.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.co.swm.member.validator.PasswordMatches;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class MemberDTO {
    private Long no;

    //@NotBlank : null X, 공백이 아닌 문자 포함
    @NotBlank(message =  "아이디는 필수 입력값입니다")
    @Pattern(regexp = "^(?=.*[a-z])[a-z0-9]{6,20}$", message = "영문 소문자 + 숫자 6 ~ 20자리여야 합니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$", message = "영문+숫자+특수문자를 포함하여 8~20자로 입력하세요.")
    private String userPwd;

    // 권한
    private String role;
}
