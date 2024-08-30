package kr.co.swm.model.dto;

import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
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
    private String accommodationInfo;

    private String roadName;
    private String region;
    private double lat;     // 위도
    private double lng;     // 경도

    private List<String> accommodationFacilities;
    private int accommodationViews;

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
    private String roomCategory;
    private int roomValues;                  // 동일 객실 개수
    private List<Integer> roomCount;         // 객실 개수
    private int endIndex;                    // 객실별 이미지 개수

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

    // mypage에서 예약내역을 불러오기 위해 추가
    private AccommodationImageDto accommodationImageDto;

    // 생성자
    public SellerDto() {
        this.roomNo = 0;
        this.roomName = "";
        this.extraRates = new ArrayList<>();
    }


    public SellerDto changeRate(int weekdayRate, int fridayRate, int saturdayRate, int sundayRate) {
        this.weekdayRate = weekdayRate;
        this.fridayRate = fridayRate;
        this.saturdayRate = saturdayRate;
        this.sundayRate = sundayRate;
        return this;
    }

    public int getCategoryNo(SellerDto room, int categoryNo) {
        if ("오션뷰".equals(room.getRoomCategory())) {
            categoryNo = 1;
        } else if ("리버뷰".equals(room.getRoomCategory())) {
            categoryNo = 2;
        } else if ("시티뷰".equals(room.getRoomCategory())) {
            categoryNo = 3;
        } else if ("마운틴뷰".equals(room.getRoomCategory())) {
            categoryNo = 4;
        }

        return categoryNo;
    }

    public String changeCategoryNo(SellerDto room, int categoryNo) {
        System.out.println("nopp : " + categoryNo);
        if (categoryNo == 1) {
            room.setRoomTypeName("오션뷰");
        } else if (categoryNo == 2) {
            room.setRoomTypeName("리버뷰");
        } else if (categoryNo == 3) {
            room.setRoomTypeName("시티뷰");
        } else if (categoryNo == 4) {
            room.setRoomTypeName("마운틴뷰");
        }
        System.out.println("kkk : " + room.getRoomTypeName());
        return room.getRoomTypeName();
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

// □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□
// □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

}


