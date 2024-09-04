package kr.co.swm.reservation.controller;

import kr.co.swm.reservation.model.dto.AccessTokenResponse;
import kr.co.swm.reservation.model.dto.PaymentRequest;
import kr.co.swm.reservation.model.dto.PaymentVerificationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class PaymentController {

    @PostMapping("/payments/complete")
    public ResponseEntity<?> completePayment(@RequestBody PaymentRequest paymentRequest) {

        try {
            // 포트원(아임포트) API를 통해 결제 정보를 확인합니다.
            String impUid = paymentRequest.getImp_uid();

            // 포트원(아임포트) API URL
            String url = "https://api.iamport.kr/payments/" + impUid;

            // 액세스 토큰 발급
            String accessToken = getAccessToken();

            // API 요청을 위한 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            // API 호출
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<PaymentVerificationResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, PaymentVerificationResponse.class);

            // 결제 정보 검증
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                PaymentVerificationResponse verificationResponse = response.getBody();

                // 검증 결과에 따른 처리
                if (verificationResponse.getResponse().getStatus().equals("paid")) {
                    // 승인 번호 반환
                    return ResponseEntity.ok().body(verificationResponse.getResponse().getApply_num());
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 검증 실패");
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 정보 조회 실패");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }


    @PostMapping("/refund/complete")
    public ResponseEntity<?> refundPayment(@RequestBody Map<String, String> request) {
        try {
            String impUid = request.get("imp_uid");

            // 포트원 API를 통해 환불 요청
            String url = "https://api.iamport.kr/payments/cancel";

            // 액세스 토큰을 발급
            String accessToken = getAccessToken();

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer " + accessToken);

            // 요청 데이터 설정
            String requestJson = "{\"imp_uid\": \"" + impUid + "\"}";

            // HTTP 요청 보내기
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            // 응답 반환
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("환불 요청 중 오류가 발생했습니다.");
        }
    }


    @Value("${iamport.api.key}")
    private String impKey;

    @Value("${iamport.api.secret}")
    private String impSecret;

    public String getAccessToken() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        String requestJson = String.format("{\"imp_key\": \"%s\", \"imp_secret\": \"%s\"}", impKey, impSecret);
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
