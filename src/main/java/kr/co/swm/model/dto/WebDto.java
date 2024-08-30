package kr.co.swm.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebDto {

//  ==================  계정  ==================

    // 사용자 계정 정보
    private int userNo;
    private String userId;
    private String userName;
    private String userPhone;
    private String userRoles;

    // 숙소 관리자 계정 정보
    private int sellerNo;
    private String sellerId;
    private String sellerName;
    private String sellerPhone;
    private String sellerRoles;

    // 웹서버 관리자 계정 정보
    private int adminNo;
    private String adminId;
    private String adminPwd;
    private String adminRoles;

//  ==================  업소  ==================
    private int accommodationNo;
    private String accommodationName;
    private String accommodationType;
    private String accommodationPhone;

//  ==================  매출 정보  ==================
    private int bookingNo;
    private int reservationAmount;
    private String reservationDate;
    private String reservationStatus;
    private String viewsDate;
    private int viewsCount;

    private int cancellationCount;
    private int reservationCount;

    private int monthlyRevenue;
    private int dailyRevenue;

    private String revenueMonth;

//  ==================  쿠폰 정보  ==================
    private int couponId;
    private String couponCode;
    private String couponName;
    private String couponType;
    private int couponDiscount;
    private int couponQuantity;
    private int couponUseQuantity;
    private int couponLeftOver;
    private String couponStartDate;
    private String couponEndDate;
    private int couponMinimumAmount;

    private String couponNo;
    private String useYN;
}
