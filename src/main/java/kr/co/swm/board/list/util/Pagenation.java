package kr.co.swm.board.list.util;

import kr.co.swm.board.list.model.dto.PageInfoDto;
import org.springframework.stereotype.Component;

@Component
public class Pagenation {
    public PageInfoDto getPageInfo
            (int listCount, int currentPage, int pageLimit, int boardLimit) {
        int maxPage = (int)(Math.ceil((double) listCount/boardLimit));
        int startPage = (currentPage-1) / pageLimit * pageLimit+1;
        int endPage = startPage+pageLimit-1;
        int row = listCount - (currentPage-1) * boardLimit;
        int offset = (currentPage-1) * boardLimit;
        int limit = offset+10;

        if(endPage>maxPage){
            endPage = maxPage;
        }

        return new PageInfoDto
                (listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage, row, offset, limit);
    }
}
