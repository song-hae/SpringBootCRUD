package demo.SpringBootWithAWS.web;

import demo.SpringBootWithAWS.web.dto.HelloResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","Hello World");
        return "hello";
    }

    @GetMapping("hello-dto")
    @ResponseBody //http의 body에 직접 넣겠다
    public HelloResponseDto helloResponseDto(@RequestParam("name") String name, @RequestParam("year") int year) {
        return new HelloResponseDto(name, year);
    }

    @GetMapping("hello-api")
    @ResponseBody //json
    public Hello helloApi(@RequestParam("name") String name){
        return new Hello(name);
    }

    @Getter @Setter @AllArgsConstructor
    static class Hello{
        private String name;
    }

}
