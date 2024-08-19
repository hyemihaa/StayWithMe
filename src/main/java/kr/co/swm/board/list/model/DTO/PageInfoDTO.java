package kr.co.swm.board.list.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageInfoDTO {
    private int listCount;  // 전체 게시글 수
    private int currentPage;    // 현재 페이지
    private int pageLimit;  // 한 페이지에 보여질 페이지 수
    private int boardLimit; // 한 페이지에 들어갈 게시글 수

    private int maxPage;    // 전체페이지
    private int startPage;  // 시작페이지
    private int endPage;    // 끝페이지

    private int row;    //  꺼낼 게시글 번호
    private int offset; // DB에서 데이터를 어디서부터 가져오는지
    private int limit;  // DB에서 데이터를 어디까지 가져오는지
}
