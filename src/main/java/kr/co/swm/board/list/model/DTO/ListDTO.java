package kr.co.swm.board.list.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ListDTO {
    private int boardNo;    //  리스트 번호(게시글 고유번호)
    private String boardName;   //  숙박 명
    private String boardType;    //  객실 유형(호텔/모텔/펜션 etc..)
    private String boardAddress;    //  숙박 주소
    private String boardCheckIn;   //  체크인 시간
    private int boardCount; //  최저 기본 가격

    private String checkinDate;  // 체크인 날짜
    private String checkoutDate; // 체크아웃 날짜

    private String filePath;    // 파일경로
    private String fileName;    // 파일 명
}
