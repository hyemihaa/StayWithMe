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
}
