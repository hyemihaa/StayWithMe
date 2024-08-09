package kr.co.swm.board.mapper;

import kr.co.swm.board.detail.model.dto.DetailDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DetailMapper {
    public List<DetailDto> getPlace();

    public List<DetailDto> getRate();
}
