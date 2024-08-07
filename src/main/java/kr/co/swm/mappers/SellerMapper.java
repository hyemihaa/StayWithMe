package kr.co.swm.mappers;

import jakarta.servlet.http.HttpSession;
import kr.co.swm.model.dto.SellerDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SellerMapper {

    List<SellerDto> basicRate(String roomCode);

    int roomInventorySearch(String adminCode);

    int basicRateUpdate(SellerDto sellerDto, int roomInventory);

    int extraRateInsert(SellerDto sellerDto);
}
