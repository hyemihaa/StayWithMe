package kr.co.swm.member.util;

import java.security.SecureRandom;

// 임시 비밀번호 생성

public class PasswordUtils {
    // 비밀번호에 사용할 문자들 (대문자, 소문자, 숫자, 특수문자 포함)
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
    // 생성할 비밀번호의 길이 (12자)
    private static final int PASSWORD_LENGTH = 12;

    public static String generateTemporaryPassword() {
        // 보안 강화를 위해 SecureRandom 사용 (난수 생성기)
        SecureRandom random = new SecureRandom();
        // 비밀번호를 저장할 StringBuilder
        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);

        // PASSWORD_LENGTH 만큼의 문자를 랜덤하게 선택하여 비밀번호 생성
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length()); // CHARACTERS에서 랜덤한 인덱스 선택
            sb.append(CHARACTERS.charAt(index)); // 선택된 문자를 StringBuilder에 추가
        }

        // 생성된 비밀번호를 문자열로 변환하여 반환
        return sb.toString();
    }
}
