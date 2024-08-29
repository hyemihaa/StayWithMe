package kr.co.swm.board.list.model.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchDTO {
     private String boardType = "전체";
     private int minRate = 0;
     private int maxRate = 1000000;
     private List<String> options;
}
