package kr.co.swm.board.detail.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class DetailDTO {
    private int boardNo;    //  리스트 번호(게시글 고유번호)
    private String boardName;   //  숙박 명
    private String boardType;    //  객실 유형(호텔/모텔/펜션 etc..)
    private String boardRoomType;    //  객실 종류(트윈베드,더블룸,디럭스룸 etc..)
    private int boardRoomNo; // 객실 종류의 고유 번호
    private String boardAddress;    //  숙박 주소
    private String boardCheckIn;   //  체크인 시간
    private String boardCheckOut;   //  체크아웃 시간
    private int boardCount; //  기본 가격
    private int boardMinPerson;  // 기본인원
    private int boardMaxPerson;  // 최대인원
    private String facilityName;  // 부대시설
    private String boardInfo;   // 정보
    private int roomNo;

    public DetailDTO(String boardCheckIn, String boardCheckOut) {
        this.boardCheckIn = boardCheckIn;
        this.boardCheckOut = boardCheckOut;
    }

    private int imageNo;
    private String accommodationImageName;    // 업소 사진 파일 명
    private String roomsImageName;    // 객실 사진 파일 명
    private String imagePath = "/accommodationImages/";     // 사진 파일경로


    private double lat;   // 위도
    private double lon;  // 경도


}
