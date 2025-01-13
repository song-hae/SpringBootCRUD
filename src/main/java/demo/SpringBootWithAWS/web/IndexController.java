package demo.SpringBootWithAWS.web;

import demo.SpringBootWithAWS.config.auth.LoginUser;
import demo.SpringBootWithAWS.config.auth.dto.SessionUser;
import demo.SpringBootWithAWS.domain.posts.PostsRepository;
import demo.SpringBootWithAWS.service.PostsService;
import demo.SpringBootWithAWS.web.dto.PostsResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts",postsService.findAllDesc());
        if(user != null) {
            model.addAttribute("userName",user.getName()); //user가 null이 아니라는 것은 로그인 되어 있다는 의미
        }
        return "index";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto responseDto = postsService.findById(id);
        model.addAttribute("posts",responseDto);
        return "posts-update";
    }
}
