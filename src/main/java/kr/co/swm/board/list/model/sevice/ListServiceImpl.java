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

    // 검색 조건에 부합하는 게시물 수 조회
//    @Override
//    public int getListCount(MainSearchDTO mainSearchDTO) {
//
//
//
//        int listCount = listMapper.getListCount(mainSearchDTO);
//
//        System.out.println("========== ServiceImpl List Count ==========");
//        System.out.println(listCount);
//        System.out.println("==========================================");
//        return listCount;
//    }

    // 검색 조건에 부합하는 게시물 정보 조회
    @Override
    public List<ListDTO> getList(MainSearchDTO mainSearchDTO, int boardLimit, int offset) {

        System.out.println("<<<<<< ServiceImpl MainSearchDTO >>>>>>>>");
        System.out.println("Main Search : " + mainSearchDTO.getMainSearch());
        System.out.println("BoardType : " + mainSearchDTO.getType());
        System.out.println("Personnel : " + mainSearchDTO.getPersonnel());
        System.out.println("Check In Date : " + mainSearchDTO.getCheckInDate());
        System.out.println("Check Out Date : " + mainSearchDTO.getCheckOutDate());
        System.out.println("<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>");

        // Step 1: 기본 조건으로 숙소 목록을 조회
        List<ListDTO> basicList = listMapper.getList(mainSearchDTO, boardLimit, offset);

        // Step 2: 부대시설, 체크인/체크아웃 조건, 인원 수 등 추가 필터링
        List<ListDTO> finalList = basicList.stream()
                .filter(accommodation -> {
                    boolean dateCondition = checkDateCondition(accommodation, mainSearchDTO);
                    boolean personnelCondition = accommodation.getPersonnel() >= mainSearchDTO.getPersonnel();
                    return dateCondition && personnelCondition;
                }).collect(Collectors.toList());

        return finalList;

//        List<ListDTO> getList = listMapper.getList(mainSearchDTO, boardLimit, offset);
//        for(ListDTO item : getList) {
//            System.out.println("========== ServiceImpl Get List ==========");
//            System.out.println("Board Name : " + item.getBoardName());
//            System.out.println("Board Type : " + item.getBoardType());
//            System.out.println("Board Address : " + item.getBoardAddress());
//            System.out.println("Board Count : " + item.getBoardCount());
//            System.out.println("==========================================");
//        }
//
//        return getList;
    }

    // 검색 조건에 부합하는 게시물 부대시설 조회
    @Override
    public List<String> getFacilities(MainSearchDTO mainSearchDTO) {
        List<String> facilities = listMapper.getFacilities(mainSearchDTO);
        return facilities.stream().distinct().collect(Collectors.toList());
    }

    // 날짜 조건을 확인하는 메서드
    private boolean checkDateCondition(ListDTO accommodation, MainSearchDTO mainSearchDTO) {
        LocalDate checkInDate = mainSearchDTO.getCheckInDate();
        LocalDate checkOutDate = mainSearchDTO.getCheckOutDate();

        // accommodation의 checkinDate와 checkoutDate가 null인지 먼저 확인
        LocalDate accommodationCheckIn = accommodation.getCheckinDate();
        LocalDate accommodationCheckOut = accommodation.getCheckoutDate();

        if (accommodationCheckIn == null || accommodationCheckOut == null) {
            // 만약 날짜가 null이면, 기본적으로 true를 반환해서 필터링에서 제외되지 않도록 처리
            return true;
        }

        // 실제 날짜 비교 로직
        return (accommodationCheckIn.isBefore(checkOutDate) && accommodationCheckOut.isAfter(checkInDate));
    }

}
