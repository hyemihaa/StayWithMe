package kr.co.swm.mappers;

import kr.co.swm.model.dto.SellerDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellerMapper {

//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 객실 및 조회수 조회
    int roomViews(@Param("accommodationNo") int accommodationNo);

    // 예약 및 결제 정보 조회
    List<SellerDto> reserveData(@Param("accommodationNo") int accommodationNo);


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 예약 검색 조회
    List<SellerDto> reservationSearch(@Param("accommodationNo") int accommodationNo,
                                      @Param("dateType") String dateType,
                                      @Param("startDate") String startDate,
                                      @Param("endDate") String endDate,
                                      @Param("searchKeyword") String searchKeyword,
                                      @Param("reservationStatus") String reservationStatus);


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□


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


//  □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□

    // 기간 수정 페이지 로드에 필요한 자료 조회
    List<SellerDto.ExtraDto> extraSeasonList(@Param("accommodationNo") int accommodationNo);

    // 추가 요금 항목 삭제(EXTRA NAME 기준)
    int extraRateDelete(@Param("extraName") String extraName, @Param("accommodationNo") int accommodationNo);

    // 추가 요금 항목 모든 조회(관리자 번호에 관련한 자료)
    List<SellerDto.ExtraDto> extraTableSearch(@Param("accommodationNo") int accommodationNo);

    // 기간 수정에 따른 DB 데이터 수정
    int extraSeasonUpdate(SellerDto.ExtraDto extraDto);


}

