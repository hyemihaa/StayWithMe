package kr.co.swm.testMvc.mapper;


import kr.co.swm.testMvc.TestDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {

    public List<TestDto> selectAll();
}
