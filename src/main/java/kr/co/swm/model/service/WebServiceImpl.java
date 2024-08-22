package kr.co.swm.model.service;

import kr.co.swm.mappers.WebMapper;
import kr.co.swm.model.dto.WebDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class WebServiceImpl implements WebService {

    private final WebMapper mapper;

    @Autowired
    public WebServiceImpl(WebMapper mapper) {
        this.mapper = mapper;
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @Override
    public WebDto dashboardData(String today) {
        return mapper.dashboardData(today);
    }

    @Override
    public List<WebDto> getAccommodationRevenueData(String today) {
        return mapper.getAccommodationRevenueData(today);
    }

    @Override
    public List<WebDto> getMonthlySalesData(String currentYear) {
        return mapper.getMonthlySalesData(currentYear);
    }

    @Override
    public List<WebDto> getRecentSalesData(String today) {
        return mapper.getReservationDataByDate(today);
    }


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 쿠폰 리스트 조회
    @Override
    public List<WebDto> couponList() {
        return mapper.couponList();
    }

    // 쿠폰 등록
    @Override
    public void couponInsert(WebDto webDto) {
        // 쿠폰 코드 생성 로직
        String couponCode = generateCouponCode(webDto);
        webDto.setCouponCode(couponCode);

        // 쿠폰 정보 DB에 삽입
        mapper.couponInsert(webDto);
    }

    private String generateCouponCode(WebDto webDto) {
        // 날짜와 고유 ID를 사용하여 쿠폰 코드 생성 (예: CP202408220001)
        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int uniqueId = mapper.countCoupons() + 1; // 쿠폰 개수에 따라 고유 ID 증가
        return "CP" + currentDate + String.format("%04d", uniqueId);
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 사용자 조회 =======================================================
    @Override
    public List<WebDto> userSearch() {
        return mapper.getUserSearch();
    }

    @Override
    public List<WebDto> sellerSearch() {
        return mapper.getSellerSearch();
    }

    @Override
    public List<WebDto> managerSearch() {
        return mapper.getManagerSearch();
    }



    // 사용자 검색 조회 =======================================================
    @Override
    public List<WebDto> searchUsersByKeyword(String keyword) {
        return mapper.searchUsersByKeyword(keyword);
    }

    @Override
    public List<WebDto> searchSellersByKeyword(String keyword) {
        return mapper.searchSellersByKeyword(keyword);
    }


}
