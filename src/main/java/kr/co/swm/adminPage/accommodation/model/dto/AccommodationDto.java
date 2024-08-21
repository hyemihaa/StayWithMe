package kr.co.swm.adminPage.accommodation.model.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccommodationDto {

    /**
     * 업소
     */
    private int acAdminNo = 23 ;
    private String accommodationInfo;         // 업소 정보

    private int accommodationNo;         // 업소 넘버
    private String accommodationName;         // 업소 이름
    private List<String> accommodationType;         // 업소 부대시설
    private String accommodationPhone;        // 업소 전화번호

    private String roadName;     // 도로명 주소
    private String region;       // 지번 주소
    private String lat;         // 위도
    private String lon;         // 경도

    private int views;         // 조회수

    private int standardOccupation;         // 기준인원
    private int maxOccupation;         // 최대인원



    /**
     * 객실
     */
    private int roomNo;         // 객실 넘버

    private List<Integer> roomCount;         // 객실 개수
    private int roomValues;             // 동일 객실 개수

    private String checkInTime;         // 체크인 시간
    private String checkOutTime;         // 체크아웃 시간
    private String roomName;         // 객실 이름

    private int weekdayRate;         // 주중 가격
    private int fridayRate;         // 금요일 가격
    private int saturdayRate;         // 토요일 가격
    private int sundayRate;         // 일요일 가격
    private String roomCategory;        // 객실 카테고리

    private int endIndex;       // 객실별 이미지 개수

    public AccommodationDto(int weekdayRate, int fridayRate, int saturdayRate, int sundayRate) {
        this.weekdayRate = weekdayRate;
        this.fridayRate = fridayRate;
        this.saturdayRate = saturdayRate;
        this.sundayRate = sundayRate;
    }

}
