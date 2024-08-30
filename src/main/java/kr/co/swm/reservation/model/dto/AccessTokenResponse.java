package kr.co.swm.reservation.model.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccessTokenResponse {

    private AccessTokenData response;

    @Getter
    @Setter
    public static class AccessTokenData {
        private String access_token;
        private int now;
        private int expired_at;
    }
}
