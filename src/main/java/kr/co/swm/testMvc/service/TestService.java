package kr.co.swm.testMvc.service;



import kr.co.swm.testMvc.TestDto;
import kr.co.swm.testMvc.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {


    private final TestMapper testMapper;

    @Autowired
    public TestService(TestMapper testMapper) {
        this.testMapper = testMapper;
    }

    public List<TestDto> selectAll() {
        return testMapper.selectAll();
    }




}
