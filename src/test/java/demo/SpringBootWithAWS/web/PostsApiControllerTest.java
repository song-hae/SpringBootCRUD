package demo.SpringBootWithAWS.web;

import demo.SpringBootWithAWS.domain.posts.Posts;
import demo.SpringBootWithAWS.domain.posts.PostsRepository;
import demo.SpringBootWithAWS.service.PostsService;
import demo.SpringBootWithAWS.web.dto.PostsSaveRequestDto;
import demo.SpringBootWithAWS.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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

    @Test
    @DisplayName("게시물 등록")
    public void savePost() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                        .title(title).content(content).author("songhae").build();
        String url = String.format("http://localhost:%d/api/v1/posts", port);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(1L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("게시물 수정")
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
        ResponseEntity<Long> exchangeEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(exchangeEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(exchangeEntity.getBody()).isEqualTo(1L);

        Posts updatedPosts = postsRepository.findById(updateId).get();
        assertThat(updatedPosts.getTitle()).isEqualTo(expectedTitle);
        assertThat(updatedPosts.getContent()).isEqualTo(expectedContent);
    }

    @Test
    @DisplayName("게시물 삭제")
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
