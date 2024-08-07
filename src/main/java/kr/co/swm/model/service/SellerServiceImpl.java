package kr.co.swm.model.service;

import jakarta.servlet.http.HttpSession;
import kr.co.swm.mappers.SellerMapper;
import kr.co.swm.model.dto.SellerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService{

    private final SellerMapper mapper;

    @Autowired
    public SellerServiceImpl(SellerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<SellerDto> basicRate(String roomCode) {
        return mapper.basicRate(roomCode);
    }

    @Override
    public int basicRateInsert(SellerDto sellerDto, String adminCode) {
        // roomInventorySearch가 null을 반환할 수 있으므로 Integer로 받음
        int roomInventory = mapper.roomInventorySearch(adminCode);

        System.out.println("---------------------------------------");
        System.out.println(roomInventory);
        System.out.println("---------------------------------------");

        // 기본 요금을 업데이트
        int basicRateUpdate = mapper.basicRateUpdate(sellerDto, roomInventory);

        System.out.println("---------------------------------------");
        System.out.println(basicRateUpdate);
        System.out.println(sellerDto.getExtraName());
        System.out.println("---------------------------------------");

        // 추가 요금이 있다면 extra_rate 테이블에 삽입
        if (basicRateUpdate > 1 && sellerDto.getExtraName() != null && !sellerDto.getExtraName().isEmpty()) {
            // sellerDto에 포함된 추가 요금 정보를 삽입
            sellerDto.setRoomRateInventoryNo(roomInventory);

            int extraRateInsert = mapper.extraRateInsert(sellerDto);

            System.out.println("---------------------------------------");
            System.out.println(extraRateInsert);
            System.out.println("---------------------------------------");

            return extraRateInsert;
        }
        return roomInventory;
    }








}
