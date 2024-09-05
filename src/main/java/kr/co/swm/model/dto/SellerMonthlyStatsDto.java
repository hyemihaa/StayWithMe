package kr.co.swm.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SellerMonthlyStatsDto {
    private List<String> monthlyLabels;
    private List<Integer> monthlyReservationCounts;
    private List<Integer> monthlyCancelCounts;
    private List<Integer> monthlyPaymentCounts;
}
