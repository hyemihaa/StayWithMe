package kr.co.swm.board.list.model.sevice;

import kr.co.swm.board.list.model.dto.ListDto;
import kr.co.swm.board.list.model.dto.PageInfoDto;

import java.util.List;

public interface ListService {
    //장소 불러오기
    List<ListDto> getPlace(PageInfoDto pi);
    // 별점 불러오기
//    List<ListDto> getRate(PageInfoDto pi);

    int getTotalCount();

}
