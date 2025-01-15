package demo.SpringBootWithAWS.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.SpringBootWithAWS.domain.posts.Posts;
import demo.SpringBootWithAWS.domain.posts.PostsRepository;
import demo.SpringBootWithAWS.service.PostsService;
import demo.SpringBootWithAWS.web.dto.PostsSaveRequestDto;
import demo.SpringBootWithAWS.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private PostsService postsService;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("게시물 등록")
    @WithMockUser("roles=USER")
    public void savePost() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                        .title(title).content(content).author("songhae").build();
        String url = String.format("http://localhost:%d/api/v1/posts", port);

        //when
        //로그인 기능 이전 테스트
        //ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                                .andExpect(status().isOk());

        //then
        //로그인 기능 이전 테스트
        // assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isEqualTo(1L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.size()).isGreaterThan(0);
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("게시물 수정")
    @WithMockUser("roles=USER")
    public void updatePost() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title").content("content").author("author").build());
        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";
        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle).content(expectedContent).build();
        String url = String.format("http://localhost:%d/api/v1/posts/%s", port, updateId);

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        //로그인 기능 이전 테스트
        //ResponseEntity<Long> exchangeEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        //assertThat(exchangeEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(exchangeEntity.getBody()).isEqualTo(1L);

        Posts updatedPosts = postsRepository.findById(updateId).get();
        assertThat(updatedPosts.getTitle()).isEqualTo(expectedTitle);
        assertThat(updatedPosts.getContent()).isEqualTo(expectedContent);
    }

    @Test
    @DisplayName("게시물 삭제")
    @WithMockUser("roles=USER")
    public void deletePost() throws Exception {
        //given
        String title = "title";
        String content = "content";
        Posts savedPosts = postsRepository.save(Posts.builder().title(title).content(content).author("songhae").build());

        //등록되었는지
        List<Posts> all = postsRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        //when
        postsService.delete(savedPosts.getId());

        //then, 삭제되었는지
        all = postsRepository.findAll();
        assertThat(all.size()).isEqualTo(0);


    }
}
