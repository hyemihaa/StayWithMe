package kr.co.swm.board.list.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MainSearchDTO {
    private String boardType = "";
    private int minRate = 0;
    private int maxRate = 1000000;
    private List<String> options;

    private String mainSearch;
    private String checkInDate;
    private String checkOutDate;
}
