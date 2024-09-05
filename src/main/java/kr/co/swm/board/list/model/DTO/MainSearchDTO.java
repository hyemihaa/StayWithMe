package kr.co.swm.board.list.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainSearchDTO {
    private String type = "";
    private int minRate = 0;
    private int maxRate = 1000000;
    private List<String> options;

    private String mainSearch;
    private int personnel;
    private String checkInDate;
    private String checkOutDate;

    // 페이징 관련 변수 추가
    private int currentPage = 1;  // 현재 페이지 (기본값 1)
    private int boardLimit = 10;  // 한 번에 로드할 게시물 수 (기본값 10)
    private int offset;
}
