package kr.co.swm.model.service;

import kr.co.swm.model.dto.SellerDto;

import java.util.List;

public interface SellerService {

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

}
