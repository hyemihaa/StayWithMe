package kr.co.swm.board.detail.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class DetailDto {
    private int BoardNo;    //  리스트 번호(게시글 고유번호)
    private String BoardName;   //  숙박 명
    private String BoardType;    //  객실 유형(호텔/모텔/펜션 etc..)
    private int BoardRating;    //  숙박 별점
    private String BoardAddress;    //  숙박 주소(도/시)
    private String BoardMiddleAddress; //  숙박 주소(시/군/구)
    private String BoardCheckIn;   //  체크인 시간
    private int BoardCount; //  기본 가격
    private int BoardDiscount;  //  쿠폰 적용가격

    private int BoardMinPerson;  // 기본인원
    private int BoardMaxPerson;  // 최대인원

    private String FilePath;    // 파일경로
    private String FileName;    // 파일 명


    private int lat;   // 위도
    private int lon;  // 경도
}
