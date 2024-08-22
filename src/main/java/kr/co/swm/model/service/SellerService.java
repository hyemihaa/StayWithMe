package kr.co.swm.model.service;

import kr.co.swm.model.dto.SellerDto;

import java.util.List;

public interface SellerService {

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 업소 조회수
    int roomViews(Long accommodationNo);

    // 대시보드 정보 조회
    List<SellerDto> mainList(Long accommodationNo);

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 예약 검색 조회
    List<SellerDto> reservationSearch(Long accommodationNo, String dateType, String startDate, String endDate, String searchKeyword, String reservationStatus);

    // 일별 예약 조회
    List<SellerDto> roomData(Long accommodationNo, String selectedDate);

    // 관리자 보유 객실 조회
    List<SellerDto> accommodationRoomData(Long accommodationNo);

    // 월별 예약 조회
    List<SellerDto> monthlyData(Long accommodationNo);

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 객실 리스트 조회
    List<String> roomNameSearch(Long accommodationNo);

    // 기본 요금 조회
    List<SellerDto> basicRateList(String roomName, Long accommodationNo);

    // 추가 요금 조회
    List<SellerDto> extraRateList(String roomName, Long accommodationNo);

    // 기본 요금 업데이트
    int basicRateUpdate(SellerDto sellerDto, Long accommodationNo);

    // 추가 요금 업데이트 또는 삽입
    int extraRateUpdate(SellerDto sellerDto, Long accommodationNo);


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 추가 요금 관련 리스트 조회
    List<SellerDto.ExtraDto> extraSeasonList(Long accommodationNo);

    // 추가 요금 항목 삭제(extraName 기준)
    boolean extraRateDelete(String extraName, Long accommodationNo);

    // 추가 요금 기간 수정(extraDateStart, extraDateEnd)
    boolean extraSeasonUpdate(List<SellerDto.ExtraDto> extraSeasonUpdate, Long accommodationNo);

}
