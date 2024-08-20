package kr.co.swm.adminPage.accommodation.model.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccommodationDto {
    private int acAdminNo =12 ;
    private String accommodationInfo;

    private int accommodationNo;
    private String accommodationName;
    private List<String> accommodationType;

    private int standardOccupation;
    private int maxOccupation;

    private int roomNo;

    private List<Integer> roomCount;
    private int roomValues;

    private String checkInTime;
    private String checkOutTime;

    private int views;
    private String roomName;

    private int weekdayRate;
    private int fridayRate;
    private int saturdayRate;
    private int sundayRate;
    private String roomCategory;
    // 전번
    private String accommodationPhone;

    // 도로명 주소
    private String roadName;


    // 지번 주소
    private String region;


    // 위도
    private String lat;

    // 경도
    private String lon;


    public AccommodationDto(int weekdayRate, int fridayRate, int saturdayRate, int sundayRate) {
        this.weekdayRate = weekdayRate;
        this.fridayRate = fridayRate;
        this.saturdayRate = saturdayRate;
        this.sundayRate = sundayRate;
    }

    /**
     * 업소 사진
     */
//

    private int endIndex;
//    private List<MultipartFile> previewFiles;
//    private List<AccommodationImageDto> images;

}
