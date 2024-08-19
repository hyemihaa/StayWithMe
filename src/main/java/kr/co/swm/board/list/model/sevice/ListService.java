package kr.co.swm.board.list.model.sevice;

import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;

import java.util.List;

public interface ListService {
    //장소 불러오기
    List<ListDTO> getPlace(PageInfoDTO pi);
    // 별점 불러오기

    int getTotalCount();

    double getAvgRate(int boardNo);

}
