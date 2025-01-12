package demo.SpringBootWithAWS.web;

import demo.SpringBootWithAWS.domain.posts.PostsRepository;
import demo.SpringBootWithAWS.service.PostsService;
import demo.SpringBootWithAWS.web.dto.PostsResponseDto;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    private final PostsService postsService;
    private final PostsRepository postsRepository;

    public IndexController(PostsService postsService, PostsRepository postsRepository) {
        this.postsService = postsService;
        this.postsRepository = postsRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts",postsRepository.findAllDesc())   ;
        return "index";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto responseDto = postsService.findById(id);
        model.addAttribute("posts",responseDto);
        return "posts-update";
    }
}
