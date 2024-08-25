package kr.co.swm.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SellerDto {

    // 관리자 정보
    private int sellerKey;
    private String sellerId;
    private String sellerType;

    // 유저 정보
    private String userName;
    private String userPhone;

    // 숙소 정보
    private int accommodationNo;
    private String accommodationName;
    private String accommodationType;
    private String accommodationPhone;
    private String accommodationPost;
    private String accommodationAddress;
    private int accommodationViews;
    private double lat;
    private double lng;

    private String viewsDate;
    private int viewsCount;

    // 객실 정보
    private int roomNo;
    private int roomTypeNo;
    private String roomTypeName;
    private String roomName;
    private String roomCheckIn;
    private String roomCheckOut;
    private int roomPersonnel;
    private int roomMaxPersonnel;

    // 객실 요금 정보
    private int roomRateNo;
    private String basicRoomName;
    private int basicDayNo;
    private int basicRate;
    private int weekdayRate;
    private int fridayRate;
    private int saturdayRate;
    private int sundayRate;

    // 예약 정보
    private int reserveRoomNo;
    private String reserveRoomName;
    private String reserveCheckIn;
    private String reserveCheckOut;
    private int reserveAmount;
    private String reservationDate;
    private String reservationCancellationDate;
    private String reservationStatus;
    private String reservationType;

    // 설정 파일
    private String requestDetails;


    // 생성자
    public SellerDto() {
        this.roomNo = 0;
        this.roomName = "";
        this.extraRates = new ArrayList<>();
    }

// □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
// □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 추가 요금 정보
    private List<ExtraDto> extraRates = new ArrayList<>();  // 기본 초기화

    @Getter
    @Setter
    public static class ExtraDto {
        private int extraRoomNo;
        private int extraNo;
        private int extraDayNo;
        private String extraName;
        private String extraDateStart;
        private String extraDateEnd;
        private int extraRate;
        private int extraWeekdayRate;
        private int extraFridayRate;
        private int extraSaturdayRate;
        private int extraSundayRate;

        // 생성자
        public ExtraDto() {
            this.extraRoomNo = 0;
            this.extraName = "";
            this.extraDayNo = 0;
            this.extraDateStart = "";
            this.extraDateEnd = "";
            this.extraRate = 0;
            this.extraWeekdayRate = 0;
            this.extraFridayRate = 0;
            this.extraSaturdayRate = 0;
            this.extraSundayRate = 0;
        }

    }
}


