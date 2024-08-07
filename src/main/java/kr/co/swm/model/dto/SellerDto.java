package kr.co.swm.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SellerDto {

//    ◎ 관리자 정보
    private int sellerKey;
    private String sellerId;
    private String sellerType;


//    ◎ 관리자 정보
    private String acoomodationNo;
    private String acoomodationName;
    private String acoomodationPhone;
    private String acoomodationAddress;



//    ◎ 객실 정보
    private int roomNo;
    private String roomType;
    private String roomName;
    private String roomCheckIn;
    private String roomCheckOut;
    private int roomPersonnel;
    private int roomMaxPersonnel;


//    ◎ 객실 요금 정보
    private int dayNo;
    private int roomRate;


//    ◎ 객실 요금 정보
    private int weekdayRate;
    private int fridayRate;
    private int saturdayRate;
    private int sundayRate;


//    ◎ 추가 객실 요금 정보
    private int extraNo;
    private int roomRateInventoryNo;
    private String extraName;
    private String extraDateStart;
    private String extraDateEnd;
    private int extraWeekdayRate;
    private int extraFridayRate;
    private int extraSaturdayRate;
    private int extraSundayRate;
    private int extraDayNo;

}

