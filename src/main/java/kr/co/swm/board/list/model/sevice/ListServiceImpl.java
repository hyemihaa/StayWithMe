package kr.co.swm.board.list.model.sevice;


import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import kr.co.swm.board.list.model.DTO.SearchDTO;
import kr.co.swm.board.mapper.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListServiceImpl implements ListService {

    //Mapper 의존성 주입
    private final ListMapper listMapper;

    @Autowired
    public ListServiceImpl(ListMapper listMapper){
        this.listMapper = listMapper;
    }

    //장소 불러오기
    @Override
    public List<ListDTO> getPlace(PageInfoDTO pi, SearchDTO searchDTO) {
        return listMapper.getPlace(pi, searchDTO);
    }

    //별점 불러오기
    @Override
    public double getAvgRate(int boardNo) {
        return listMapper.getAvgRate(boardNo);
    }

    //게시글의 수
    @Override
    public int getTotalCount(SearchDTO searchDTO){
        return listMapper.getTotalCount(searchDTO);
    }

    //최저 기본 가격
    @Override
    public List<ListDTO> getCost() {
        return listMapper.getCost();
    }
}
