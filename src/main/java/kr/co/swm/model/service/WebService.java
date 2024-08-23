package kr.co.swm.model.service;

import kr.co.swm.model.dto.WebDto;
import java.util.List;

public interface WebService {

    WebDto dashboardData(String today);
    List<WebDto> getAccommodationRevenueData(String today);
    List<WebDto> getMonthlySalesData(String currentYear);
    List<WebDto> getRecentSalesData(String today);

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 쿠폰 리스트 조회
    List<WebDto> couponList();

    // 쿠폰 등록
    void couponInsert(WebDto webDto);

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 사용자 조회
    List<WebDto> userSearch();
    List<WebDto> sellerSearch();
    List<WebDto> managerSearch();

    // 사용자 검색 조회
    List<WebDto> searchUsersByKeyword(String keyword);
    List<WebDto> searchSellersByKeyword(String keyword);
}
