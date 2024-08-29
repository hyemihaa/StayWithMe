package kr.co.swm.board.list.model.sevice;


import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.MainSearchDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import kr.co.swm.board.list.model.DTO.SearchDTO;
import kr.co.swm.board.mapper.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    // 부대시설 불러오기
    @Override
    public List<String> getUniqueFacilities() {
        return listMapper.getUniqueFacilities();
    }

    //  체크인 & 체크아웃 지정할 때 나오는 리스트
//    @Override
//    public List<ListDTO> getCheck(String checkinDate, String checkoutDate) {
//        return listMapper.getCheck(checkinDate, checkoutDate);
//    }



    @Override
    public int getListCount(MainSearchDTO mainSearchDTO) {

        System.out.println("<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");
        System.out.println("Main Search : " + mainSearchDTO.getMainSearch());
        System.out.println("BoardType : " + mainSearchDTO.getBoardType());
        System.out.println("<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");

        int listCount = listMapper.getListCount(mainSearchDTO);

        System.out.println("========== ServiceImpl List Count ==========");
        System.out.println(listCount);
        System.out.println("==========================================");
        return listCount;
    }

    @Override
    public List<ListDTO> getList(MainSearchDTO mainSearchDTO) {

        List<ListDTO> getList = listMapper.getList(mainSearchDTO);
        for(ListDTO item : getList) {
            System.out.println("========== ServiceImpl Get List ==========");
            System.out.println("Board Name : " + item.getBoardName());
            System.out.println("Board Type : " + item.getBoardType());
            System.out.println("Board Address : " + item.getBoardAddress());
            System.out.println("Board Count : " + item.getBoardCount());
            System.out.println("==========================================");
        }

        return getList;
    }

    @Override
    public List<String> getFacilities(MainSearchDTO mainSearchDTO) {
        List<String> facilities = listMapper.getFacilities(mainSearchDTO);
        return facilities.stream().distinct().collect(Collectors.toList());
    }
}
