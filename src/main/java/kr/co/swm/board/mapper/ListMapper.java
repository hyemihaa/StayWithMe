package kr.co.swm.board.mapper;

import kr.co.swm.board.list.model.dto.ListDto;
import kr.co.swm.board.list.model.dto.PageInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ListMapper {
  public List<ListDto> getAllPosts(@Param("pi") PageInfoDto pi);
  int getTotalCount();
}
