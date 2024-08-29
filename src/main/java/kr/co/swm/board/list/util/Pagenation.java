package kr.co.swm.board.list.util;

import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import org.springframework.stereotype.Component;

@Component
public class Pagenation {
    public PageInfoDTO getPageInfo (int listCount, int currentPage, int pageLimit, int boardLimit) {

        int maxPage = (int)(Math.ceil((double) listCount/boardLimit));  //  총 페이지
        int startPage = (currentPage-1) / pageLimit * pageLimit+1;  // 시작 페이지
        int endPage = startPage + pageLimit -1;    // 끝 페이지
        int row = listCount - (currentPage-1) * boardLimit;
        int offset = (currentPage-1) * boardLimit;
        int limit = offset+10;


        // 최소 1페이지 이상이 되도록 보장
        if (maxPage < 1) {
            maxPage = 1;
        }

        //  페이지가 최대 페이지 넘기지 않게 조정
        if(endPage > maxPage){
            endPage = maxPage;
        }

        return new PageInfoDTO(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage, row, offset, limit);
    }
}
