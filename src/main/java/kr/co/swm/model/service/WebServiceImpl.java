package kr.co.swm.model.service;

import kr.co.swm.mappers.WebMapper;
import kr.co.swm.model.dto.WebDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebServiceImpl implements WebService {

    private final WebMapper mapper;

    @Autowired
    public WebServiceImpl(WebMapper mapper) {
        this.mapper = mapper;
    }

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    @Override
    public List<WebDto> dashboardData() {

        // 예약 데이터와 조회수 데이터를 매퍼를 통해 가져옵니다.
        List<WebDto> bookingData = mapper.bookingData();
        List<WebDto> views = mapper.views();

        // 최종적으로 반환할 대시보드 데이터를 저장할 리스트를 초기화합니다.
        List<WebDto> dashboardData = new ArrayList<>();

        // 오늘 날짜를 "yyyy-MM-dd" 형식으로 가져옵니다.
        LocalDate today = LocalDate.now();
        String todayStr = today.toString();

        // 오늘 날짜에 해당하는 사용자 조회수의 총합을 계산합니다.
        int totalViewsToday = views.stream()
                .filter(view -> todayStr.equals(view.getViewsDate())) // 오늘 날짜와 일치하는지 확인
                .mapToInt(WebDto::getViewsCount) // 조회수만 추출
                .sum(); // 조회수의 합계를 계산

        // 오늘 날짜에 해당하는 예약 신청, 예약 취소, 결제 금액을 계산하기 위한 변수를 초기화합니다.
        int totalBookingsToday = 0;
        int totalCancellationsToday = 0;
        int totalConfirmedAmountToday = 0;

        // 숙박 형태별 매출 현황을 저장하기 위한 맵을 초기화합니다.
        Map<String, Integer> accommodationTypeRevenue = new HashMap<>();
        // 월별 매출 현황을 저장하기 위한 맵을 초기화합니다.
        Map<String, Integer> monthlyRevenue = new HashMap<>();
        // 최근 매출 현황을 저장하기 위한 맵을 초기화합니다.
        Map<String, WebDto> recentSales = new HashMap<>();

        // 예약 데이터 리스트를 순회하며 필요한 데이터를 가공합니다.
        for (WebDto item : bookingData) {
            // 오늘 날짜와 동일한 예약 데이터만 처리합니다.
            if (todayStr.equals(item.getReservationDate())) {
                if ("Confirmed".equals(item.getReservationStatus())) {
                    // 예약이 확정된 경우
                    totalBookingsToday++; // 예약 건수 증가
                    totalConfirmedAmountToday += item.getReservationAmount(); // 결제 금액 누적
                } else if ("Cancelled".equals(item.getReservationStatus())) {
                    // 예약이 취소된 경우
                    totalCancellationsToday++; // 취소 건수 증가
                }
            }

            // 숙박 형태별 매출 현황을 계산하여 맵에 저장합니다.
            String accommodationType = item.getAccommodationType();
            if (accommodationType != null) { // 숙박 유형이 존재하는 경우에만 처리
                accommodationTypeRevenue.put(accommodationType,
                        accommodationTypeRevenue.getOrDefault(accommodationType, 0) + item.getReservationAmount());
            }

            // 예약 날짜를 월별로 그룹화하여 매출 현황을 계산하여 맵에 저장합니다.
            String month = item.getReservationDate().substring(0, 7);  // "yyyy-MM" 형식으로 월만 추출
            monthlyRevenue.put(month, monthlyRevenue.getOrDefault(month, 0) + item.getReservationAmount());

            // 최근 매출 현황을 계산하여 각 숙박 유형별로 업데이트합니다.
            WebDto salesDto = recentSales.getOrDefault(accommodationType, new WebDto());
            salesDto.setAccommodationType(accommodationType);
            salesDto.setReservationDate(todayStr); // 최근 매출 현황이므로 오늘 날짜를 설정

            if ("Confirmed".equals(item.getReservationStatus())) {
                // 확정된 예약의 경우
                salesDto.setReservationCount(salesDto.getReservationCount() + 1); // 예약 수 증가
                salesDto.setReservationAmount(salesDto.getReservationAmount() + item.getReservationAmount()); // 매출 금액 증가
            } else if ("Cancelled".equals(item.getReservationStatus())) {
                // 취소된 예약의 경우
                salesDto.setCancellationCount(salesDto.getCancellationCount() + 1); // 취소 수 증가
                salesDto.setReservationAmount(salesDto.getReservationAmount() - item.getReservationAmount()); // 매출 금액 감소
            }
            recentSales.put(accommodationType, salesDto);
        }

        // 요약 데이터를 WebDto 객체에 담아 리스트에 추가합니다.
        WebDto summaryDto = new WebDto();
        summaryDto.setViewsCount(totalViewsToday);  // 오늘 날짜의 사용자 조회수 총합
        summaryDto.setReservationCount(totalBookingsToday);  // 오늘 날짜의 예약 신청 총합
        summaryDto.setCancellationCount(totalCancellationsToday);  // 오늘 날짜의 예약 취소 총합
        summaryDto.setReservationAmount(totalConfirmedAmountToday);  // 오늘 날짜의 결제 금액 총합

        // 요약 데이터를 대시보드 리스트의 첫 번째 요소로 추가합니다.
        dashboardData.add(0, summaryDto);

        // 숙박 형태별 매출 현황 데이터를 대시보드 리스트에 추가합니다.
        dashboardData.addAll(accommodationTypeRevenue.entrySet().stream().map(entry -> {
            WebDto revenueDto = new WebDto();
            revenueDto.setAccommodationType(entry.getKey()); // 숙박 유형 설정
            revenueDto.setReservationAmount(entry.getValue()); // 해당 유형의 총 매출 금액 설정
            return revenueDto;
        }).collect(Collectors.toList()));

        // 월별 매출 현황 데이터를 대시보드 리스트에 추가합니다.
        dashboardData.addAll(monthlyRevenue.entrySet().stream().map(entry -> {
            WebDto revenueDto = new WebDto();
            revenueDto.setReservationDate(entry.getKey()); // 월 설정 (yyyy-MM 형식)
            revenueDto.setReservationAmount(entry.getValue()); // 해당 월의 총 매출 금액 설정
            return revenueDto;
        }).collect(Collectors.toList()));

        // 최근 매출 현황 데이터를 대시보드 리스트에 추가합니다.
        dashboardData.addAll(recentSales.values());

        return dashboardData; // 가공된 대시보드 데이터를 반환합니다.
    }



}
