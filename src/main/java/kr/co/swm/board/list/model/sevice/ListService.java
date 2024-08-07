package kr.co.swm.board.list.model.sevice;

import kr.co.swm.board.list.model.dto.ListDto;
import kr.co.swm.board.list.model.dto.PageInfoDto;

import java.util.List;

public interface ListService {
    //목록 불러오기
    public List<ListDto> getAllPosts(PageInfoDto pi);
    int getTotalCount();
}
