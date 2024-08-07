package kr.co.swm.board.list.model.sevice;


import kr.co.swm.board.list.model.dto.ListDto;
import kr.co.swm.board.list.model.dto.PageInfoDto;
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


    //목록 불러오기
      @Override
      public List<ListDto> getAllPosts(PageInfoDto pi){
          return listMapper.getAllPosts(pi);
      }

        //페이징 처리
      @Override
      public int getTotalCount(){
          return listMapper.getTotalCount();
      }





}
