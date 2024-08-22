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
        List<WebDto> bookingData = mapper.bookingData();
        List<WebDto> views = mapper.views();

        List<WebDto> dashboardData = new ArrayList<>();

        LocalDate today = LocalDate.now();
        String todayStr = today.toString();

        int totalViewsToday = views.stream()
                .filter(view -> todayStr.equals(view.getViewsDate()))
                .mapToInt(WebDto::getViewsCount)
                .sum();

        int totalBookingsToday = 0;
        int totalCancellationsToday = 0;
        int totalConfirmedAmountToday = 0;

        Map<String, Integer> accommodationTypeRevenue = new HashMap<>();
        Map<String, Integer> monthlyRevenue = new HashMap<>();
        Map<String, WebDto> recentSales = new HashMap<>();

        for (WebDto item : bookingData) {
            String reservationStatus = item.getReservationStatus();
            String accommodationType = item.getAccommodationType();
            String reservationDate = item.getReservationDate();

            if (accommodationType == null || accommodationType.trim().isEmpty()) {
                accommodationType = "Unknown"; // 또는 "기타", "알 수 없음" 등으로 설정 가능
            }

            if (todayStr.equals(reservationDate)) {
                if (reservationStatus != null) {
                    reservationStatus = reservationStatus.trim().toLowerCase();
                } else {
                    reservationStatus = "";
                }

                if ("confirmed".equals(reservationStatus)) {
                    totalBookingsToday++;
                    totalConfirmedAmountToday += item.getReservationAmount();
                } else if ("cancelled".equals(reservationStatus)) {
                    totalCancellationsToday++;
                }
            }

            accommodationTypeRevenue.put(accommodationType,
                    accommodationTypeRevenue.getOrDefault(accommodationType, 0) + item.getReservationAmount());

            if (reservationDate != null) {
                String month = reservationDate.substring(0, 7);
                monthlyRevenue.put(month, monthlyRevenue.getOrDefault(month, 0) + item.getReservationAmount());
            }

            WebDto salesDto = recentSales.getOrDefault(accommodationType, new WebDto());
            salesDto.setAccommodationType(accommodationType);
            salesDto.setReservationDate(todayStr);

            if ("confirmed".equals(reservationStatus)) {
                salesDto.setReservationCount(salesDto.getReservationCount() + 1);
                salesDto.setReservationAmount(salesDto.getReservationAmount() + item.getReservationAmount());
            } else if ("cancelled".equals(reservationStatus)) {
                salesDto.setCancellationCount(salesDto.getCancellationCount() + 1);
                salesDto.setReservationAmount(salesDto.getReservationAmount() - item.getReservationAmount());
            }

            recentSales.put(accommodationType, salesDto);
        }

        WebDto summaryDto = new WebDto();
        summaryDto.setViewsCount(totalViewsToday);
        summaryDto.setReservationCount(totalBookingsToday);
        summaryDto.setCancellationCount(totalCancellationsToday);
        summaryDto.setReservationAmount(totalConfirmedAmountToday);
        dashboardData.add(summaryDto);

        dashboardData.addAll(accommodationTypeRevenue.entrySet().stream().map(entry -> {
            WebDto revenueDto = new WebDto();
            revenueDto.setAccommodationType(entry.getKey());
            revenueDto.setReservationAmount(entry.getValue());
            return revenueDto;
        }).collect(Collectors.toList()));

        dashboardData.addAll(monthlyRevenue.entrySet().stream().map(entry -> {
            WebDto revenueDto = new WebDto();
            revenueDto.setReservationDate(entry.getKey());
            revenueDto.setReservationAmount(entry.getValue());
            return revenueDto;
        }).collect(Collectors.toList()));

        dashboardData.addAll(recentSales.values());

        return dashboardData;
    }
}
