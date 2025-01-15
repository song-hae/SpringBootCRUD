package demo.SpringBootWithAWS.web;

import demo.SpringBootWithAWS.config.auth.SecurityConfig;
import demo.SpringBootWithAWS.web.dto.HelloResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// 구현
//public class HelloControllerTest {
//
//    private HelloController helloController;
//    private Model model;
//
//    @Test
//    void hello() {
//        model = new ConcurrentModel();
//        helloController = new HelloController();
//        Assertions.assertThat(helloController.hello(model)).isEqualTo("hello");
//    }

// mock을 활용 구현
//@ExtendWith(MockitoExtension.class)
//public class HelloControllerTest {
//
//    private HelloController helloController;
//
//    @Mock
//    private Model model;
//
//    @BeforeEach
//    void setUp() {
//        helloController = new HelloController();
//    }
//
//    @Test
//    void hello() {
//        // when
//        String result = helloController.hello(model);
//
//        // then
//        Assertions.assertThat(result).isEqualTo("hello");
//    }
//
//    @Test //이건 DTO 객체의 getter만 테스트하는 것임.
//        //API의 동작을 검증하지는 못 함.
//    void helloDto(){
//        //given
//        String name = "songhae";
//        int year = 2025;
//
//        //when
//        HelloResponseDto helloResponseDto = new HelloResponseDto(name, year);
//
//        //then
//        Assertions.assertThat(helloResponseDto.getName()).isEqualTo(name);
//        Assertions.assertThat(helloResponseDto.getYear()).isEqualTo(year);
//    }
//
//}

@WebMvcTest(controllers = HelloController.class, excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class HelloControllerTest {
    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    void hello() throws Exception{
        mvc.perform(get("/hello")) //"/hello" 경로로 HTTP GET 요청을 테스트로 보냄
                .andExpect(status().isOk()) //HTTP 상태 코드가 200(OK)인지 확인
                .andExpect(view().name("hello"));// View 이름 검증
        }

    @WithMockUser(roles = "USER")
    @Test
    void helloDto() throws Exception {
        mvc.perform(get("/hello-dto")
                        .param("name", "hello")
                        .param("year", "1000"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("hello"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value(1000));
    }
}





