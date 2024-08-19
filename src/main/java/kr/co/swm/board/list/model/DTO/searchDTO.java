package kr.co.swm.board.list.model.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class searchDTO {
    private String type = "boardType";
    private int currentPage = 1;
}
