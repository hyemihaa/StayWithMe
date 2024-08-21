package kr.co.swm.testMvc;


import kr.co.swm.testMvc.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TestMvcController {

    private TestService testService;


    @Autowired
    public TestMvcController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("test")
    public String test(Model model) {

        List<TestDto> users = testService.selectAll();

        model.addAttribute("users", users);
        return "test";
    }
}
