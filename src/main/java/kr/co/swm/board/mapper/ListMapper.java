package kr.co.swm.board.mapper;

import kr.co.swm.board.list.model.dto.ListDto;
import kr.co.swm.board.list.model.dto.PageInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ListMapper {
//  게시글 불러오기
  List<ListDto> getPlace(@Param("pi") PageInfoDto pi);
// 총 게시글 개수
  int getTotalCount();
// 별점 평균
  double getAvgRate(@Param("boardNo") int boardNo);
}
