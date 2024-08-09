package kr.co.swm.mappers;

import kr.co.swm.model.dto.SellerDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellerMapper {

    // 객실 리스트 조회
    List<String> roomNameSearch(int accommodationNo);

    // 기본 요금 조회
    List<SellerDto> basicRateList(String roomName);

    // 추가 요금 조회
    List<SellerDto> extraRateList(String roomName);

}
