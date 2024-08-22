package kr.co.swm.adminPage.accommodation.model.service;


import kr.co.swm.adminPage.accommodation.mapper.AccommodationMapper;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationDto;
import kr.co.swm.adminPage.accommodation.model.dto.AccommodationImageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccommodationServiceImpl {

    private final AccommodationMapper mapper;

    @Autowired
    public AccommodationServiceImpl(AccommodationMapper mapper) {
        this.mapper = mapper;
    }


    // 업소 인입
    // 업소 메인 사진 인입
    // 객실 인입 ( 1 )
    // 객실 사진 인입 ( 1, 1 )
//                 ( 1, 2 )
    public int saveAccommodation(AccommodationDto accommodationDto, AccommodationImageDto mainImage) {

        int no = accommodationDto.getAcAdminNo();
        no++;

        int result = mapper.enrollAccommodation(accommodationDto, no) ;
        if (result == 1) {
            System.out.println("origin : " + mainImage.getUploadOriginName());
            System.out.println("unique : " + mainImage.getUploadUniqueName());
            System.out.println("path : " + mainImage.getUploadImagePath());
            for (int i = 0; i < accommodationDto.getAccommodationType().size(); i++) {
                String facility = accommodationDto.getAccommodationType().get(i);
                System.out.println(accommodationDto.getAccommodationType().get(i));
                mapper.enrollFacilities(facility,no);
            }
            return mapper.enrollMainImage(mainImage, no);
        } else {
            return 0;
        }
    }

    public int enrollRooms(AccommodationDto  accommodationDto, String roomCategory, String roomName, String checkIn, String checkOut) {
        int no = accommodationDto.getAcAdminNo();
        int categoryNo = 0;
        System.out.println("service : " + roomCategory);
        if ("오션뷰".equals(roomCategory)) {
            categoryNo = 1;
        } else if ("리버뷰".equals(roomCategory)) {
            categoryNo = 2;
        } else if ("시티뷰".equals(roomCategory)) {
            categoryNo = 3;
        } else if ("마운틴뷰".equals(roomCategory)) {
            categoryNo = 4;
        }
        for (int i = 1; i <= accommodationDto.getRoomValues(); i++) {
            mapper.enrollRoom(accommodationDto,no, categoryNo, roomName, checkIn, checkOut, i);
        }
        System.out.println("NO : " + categoryNo);
        System.out.println("nameService : " + roomName);
        System.out.println("standard : " + accommodationDto.getStandardOccupation());




            return 1;
    }
    public void enrollSubImages(AccommodationImageDto roomImages, int roomNo) {

    }
}


////        public int enrollUsedBoard (UsedBoardDto usedBoard){
////
////            // 보드 인입
////            int result = usedBoardMapper.usedBoardEnrollXML(usedBoard);
////            // ?
////            usedBoardMapper.usedBoardProductEnrollXML(usedBoard);
////
////            if (result > 0) {
////                // 갯수만큼 이미지 인입
////                for (UsedBoardImageDto image : usedBoard.getImages()) {
////                    // 새로 생성된 게시물 ID 설정
////                    image.setUsedBoardId(usedBoard.getUsedBoardId());
////                    imageMapper.usedBoardEnrollImageXML(image);
////                }
////            }
////            return result > 0 ? 1 : 0; // 성공하면 1, 실패하면 0
////        }
//    }

//}
















//    public int enrollUsedBoard(AccommodationDto usedBoard){
//
//        int result = mapper.usedBoardEnrollXML(usedBoard);
//        mapper.usedBoardProductEnrollXML(usedBoard);
//        if (result > 0) {
//            for (AccommodationDto image : usedBoard.getImages()) {
////                image.setUsedBoardId(usedBoard.getUsedBoardId()); // 새로 생성된 게시물 ID 설정
//                imageMapper.usedBoardEnrollImageXML(image);
//            }
//        }
//        return result > 0 ? 1 : 0; // 성공하면 1, 실패하면 0
//}

