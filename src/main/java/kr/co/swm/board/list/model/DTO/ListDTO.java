package kr.co.swm.board.list.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListDTO {
    private int boardNo;    //  리스트 번호(게시글 고유번호)
    private String boardName;   //  숙박 명
    private String boardType;    //  객실 유형(호텔/모텔/펜션 etc..)
    private int boardRating;    //  숙박 별점
    private String boardAddress;    //  숙박 주소(도/시)
    private String boardMiddleAddress; //  숙박 주소(시/군/구)
    private String boardSubAddress; //  숙박 주소(읍/면/동)
    private String boardCheckIn;   //  체크인 시간
    private int boardCount; //  기본 가격
    private int boardDiscount;  //  쿠폰 적용가격

    private String filePath;    // 파일경로
    private String fileName;    // 파일 명


}
