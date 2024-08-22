package kr.co.swm.mappers;

import kr.co.swm.model.dto.WebDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WebMapper {

    /// 1. 요약 데이터 (조회수, 예약수, 취소수, 결제금액)
    WebDto dashboardData(@Param("today") String today);

    // 2. 숙박 형태별 매출 현황 데이터
    List<WebDto> getAccommodationRevenueData(@Param("today") String today);

    // 3. 월별 매출 현황 데이터
    List<WebDto> getMonthlySalesData(@Param("currentYear") String currentYear);

    // 4. 최근 매출 현황 데이터
    List<WebDto> getReservationDataByDate(@Param("reservationDate") String reservationDate);

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 1. 쿠폰 리스트 조회
    List<WebDto> couponList();

    // 2. 쿠폰 등록
    void couponInsert(WebDto webDto);

    // 3. 쿠폰 개수를 세기 위한 메서드
    int countCoupons();

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 1. 사용자 조회 =======================================================
    List<WebDto> getUserSearch();
    List<WebDto> getSellerSearch();
    List<WebDto> getManagerSearch();

    // 2. 사용자 검색 조회 =======================================================
    List<WebDto> searchUsersByKeyword(@Param("keyword") String keyword);
    List<WebDto> searchSellersByKeyword(@Param("keyword") String keyword);
}
