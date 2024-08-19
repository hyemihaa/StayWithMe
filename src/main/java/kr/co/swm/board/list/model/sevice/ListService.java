package kr.co.swm.board.list.model.sevice;

import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;

import java.util.List;

public interface ListService {
    //장소 불러오기
    List<ListDTO> getPlace(PageInfoDTO pi);
    // 게시글의 수
    int getTotalCount();
    // 별점 불러오기
    double getAvgRate(int boardNo);
    //  최저 기본 가격
    List<ListDTO> getCost();
}
