package kr.co.swm.board.list.model.sevice;


import kr.co.swm.board.list.model.DTO.ListDTO;
import kr.co.swm.board.list.model.DTO.MainSearchDTO;
import kr.co.swm.board.list.model.DTO.PageInfoDTO;
import kr.co.swm.board.list.model.DTO.SearchDTO;
import kr.co.swm.board.mapper.ListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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


// ========== ========== ========== /get-list ========== ========== ==========

    // 검색 조건에 부합하는 게시물 정보 조회
    @Override
    public List<ListDTO> getList(MainSearchDTO mainSearchDTO) {
        int offset = (mainSearchDTO.getCurrentPage() - 1) * mainSearchDTO.getBoardLimit();
        mainSearchDTO.setOffset(offset);
        return listMapper.getList(mainSearchDTO, mainSearchDTO.getBoardLimit(), offset);
    }


    // 검색 조건에 부합하는 게시물 부대시설 조회
    @Override
    public List<String> getFacilities(MainSearchDTO mainSearchDTO) {
        List<String> facilities = listMapper.getFacilities(mainSearchDTO);

        // 디버깅 메시지 출력
        System.out.println("========== ServiceImpl Get Facilities ==========");
        facilities.forEach(facility -> System.out.println("Facility: " + facility));
        System.out.println("===============================================");

        // 중복 제거 후 반환
        return facilities.stream().distinct().collect(Collectors.toList());
    }

}
