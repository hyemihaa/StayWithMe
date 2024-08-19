package kr.co.swm.member.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*비밀번호와 비밀번호 확인이 일치하는지 검증하는 커스텀 유효성 검사 어노테이션 생성*/

// 어노테이션을 ElementType.TYPE : 클래스, 인터페이스, enum에 적용할 수 있도록 지정
@Target({ElementType.TYPE})
// 어노테이션 유지 여부, RetentionPolicy.RUNTIME : 런타임동안 유지
@Retention(RetentionPolicy.RUNTIME)
// 유효성 검사 로직이 구현된 클래스 지정
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface PasswordMatches {

    // 유효성 검사 실패 시 반환될 기본 에러 메세지
    String message() default "비밀번호와 비밀번호 확인이 일치하지 않습니다.";
    Class<?>[] groups() default {}; // 유효성 검사 그룹 지정 (기본값은 그룹 지정 없음)
    Class<? extends Payload>[] payload() default {}; // 추가적인 메타데이터를 전달할 때 사용 (기본값은 빈 배열)

}
