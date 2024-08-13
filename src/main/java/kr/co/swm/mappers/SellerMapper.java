package kr.co.swm.mappers;

import kr.co.swm.model.dto.SellerDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellerMapper {

    // 객실 리스트 조회
    List<String> roomNameSearch(int accommodationNo);

    // 기본 요금 조회
    List<SellerDto> basicRateList(@Param("roomName") String roomName, @Param("accommodationNo") int accommodationNo);

    // 추가 요금 조회
    List<SellerDto> extraRateList(@Param("roomName") String roomName, @Param("accommodationNo") int accommodationNo);

    // 객실 정보 조회(BASIC_RATE)
    List<SellerDto> bRoomInfoSearch(@Param("roomName") String str, @Param("accommodationNo") int accommodationNo);

    // 객실 정보 조회(EXTRA_RATE)
    List<SellerDto.ExtraDto> eRoomInfoSearch(@Param("roomName") String roomName, @Param("accommodationNo") int accommodationNo);

    int basicRateUpdate(SellerDto sellerDto);

    int extraRateUpdate(SellerDto.ExtraDto extraDto);

    int extraRateInsert(SellerDto.ExtraDto extraDto);


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    List<SellerDto.ExtraDto> extraSeasonList(@Param("accommodationNo") int accommodationNo);

    List<SellerDto.ExtraDto> getExtraRateInfo(@Param("accommodationNo") int accommodationNo)


}

