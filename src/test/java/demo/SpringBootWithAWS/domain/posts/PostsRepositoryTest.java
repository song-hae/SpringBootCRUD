package demo.SpringBootWithAWS.domain.posts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    private PostsRepository postsRepository;

    @BeforeEach
    public void cleanUp(){
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장, 조회 테스트")
    public void saveAndFindById(){
        //given
        String title = "title";
        String content = "content";
        Posts post = Posts.builder()
                .title(title)
                .content(content)
                .author("songhae")
                .build();

        //when
        Posts savedPost = postsRepository.save(post); //저장
        Optional<Posts> foundPost = postsRepository.findById(savedPost.getId()); //조회

        //then
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get().getTitle()).isEqualTo("title");
        assertThat(foundPost.get().getContent()).isEqualTo("content");
        assertThat(foundPost.get().getAuthor()).isEqualTo("songhae");
    }


}
