package kr.co.swm.reservation.controller;

import kr.co.swm.reservation.model.dto.AccessTokenResponse;
import kr.co.swm.reservation.model.dto.PaymentRequest;
import kr.co.swm.reservation.model.dto.PaymentVerificationResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PaymentController {

    @PostMapping("/payments/complete")
    public ResponseEntity<?> completePayment(@RequestBody PaymentRequest paymentRequest) {

        System.out.println("================");
        System.out.println("================");
        System.out.println("================");
        System.out.println("================");
        System.out.println("================");
        System.out.println("================");
        System.out.println("================");
        System.out.println("================");
        try {
            // 포트원(아임포트) API를 통해 결제 정보를 확인합니다.
            String impUid = paymentRequest.getImp_uid();
            System.out.println("impuid : " + impUid);

            // 포트원(아임포트) API URL
            String url = "https://api.iamport.kr/payments/" + impUid;
            System.out.println("url : " + url);

            // 액세스 토큰 발급
            String accessToken = getAccessToken();
            System.out.println("accessToken : " + accessToken);

            // API 요청을 위한 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            // API 호출
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<PaymentVerificationResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, PaymentVerificationResponse.class);
            System.out.println("aaa");

            // 결제 정보 검증
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            System.out.println("bbb");
                PaymentVerificationResponse verificationResponse = response.getBody();
            System.out.println("ccc");

                // 검증 결과에 따른 처리
                if (verificationResponse.getResponse().getStatus().equals("paid")) {
            System.out.println("ddd");
                    // 승인 번호 반환
                    return ResponseEntity.ok().body(verificationResponse.getResponse().getApply_num());
                } else {
            System.out.println("eeee");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 검증 실패");
                }
            } else {
            System.out.println("fffff");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 정보 조회 실패");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    private String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestJson = "{\"imp_key\": \"1682354585415623\", \"imp_secret\": \"nSB8qzVCX4Qwq3onjAmpvY0UoLz3uDutGhAmNEpUv5Rvz89E5r4zF74CEEks2wJWdpmcUm3gPvkX1rr4\"}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<AccessTokenResponse> response = restTemplate.postForEntity(
                "https://api.iamport.kr/users/getToken", entity, AccessTokenResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getResponse().getAccess_token();
        } else {
            throw new RuntimeException("포트원(아임포트) 액세스 토큰 발급 실패");
        }
    }

}
