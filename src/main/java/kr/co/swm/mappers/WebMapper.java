package kr.co.swm.mappers;

import kr.co.swm.model.dto.WebDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WebMapper {

    // 매출 정보 조회
    List<WebDto> bookingData();

    // 조회수 조회
    List<WebDto> views();

}
