package kr.co.swm.model.service;

import kr.co.swm.model.dto.SellerDto;

import java.util.List;

public interface SellerService {

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 업소 조회수
    int roomViews(int accommodationNo);

    // 대시보드 정보 조회
    List<SellerDto> mainList(int accommodationNo);

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 예약 검색 조회
    List<SellerDto> reservationSearch(int accommodationNo, String dateType, String startDate, String endDate, String searchKeyword, String reservationStatus);

    // 일별 예약 조회
    List<SellerDto> roomData(int accommodationNo, String selectedDate);

    // 관리자 보유 객실 조회
    List<SellerDto> accommodationRoomData(int accommodationNo);

    // 월별 예약 조회
    List<SellerDto> monthlyData(int accommodationNo);

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 객실 리스트 조회
    List<String> roomNameSearch(int accommodationNo);

    // 기본 요금 조회
    List<SellerDto> basicRateList(String roomName, int accommodationNo);

    // 추가 요금 조회
    List<SellerDto> extraRateList(String roomName, int accommodationNo);

    // 기본 요금 업데이트
    int basicRateUpdate(SellerDto sellerDto, int accommodationNo);

    // 추가 요금 업데이트 또는 삽입
    int extraRateUpdate(SellerDto sellerDto, int accommodationNo);


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 추가 요금 관련 리스트 조회
    List<SellerDto.ExtraDto> extraSeasonList(int accommodationNo);

    // 추가 요금 항목 삭제(extraName 기준)
    boolean extraRateDelete(String extraName, int accommodationNo);

    // 추가 요금 기간 수정(extraDateStart, extraDateEnd)
    boolean extraSeasonUpdate(List<SellerDto.ExtraDto> extraSeasonUpdate, int accommodationNo);

}
