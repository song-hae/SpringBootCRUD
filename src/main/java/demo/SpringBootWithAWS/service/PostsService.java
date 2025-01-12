package demo.SpringBootWithAWS.service;

import demo.SpringBootWithAWS.domain.posts.Posts;
import demo.SpringBootWithAWS.domain.posts.PostsRepository;
import demo.SpringBootWithAWS.web.dto.PostsResponseDto;
import demo.SpringBootWithAWS.web.dto.PostsSaveRequestDto;
import demo.SpringBootWithAWS.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional //DB와 근접한 리포지토리에 접근하는 것이기 때문에 어노테이션 활용
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = findPostsById(id);

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts entity = findPostsById(id);

        return new PostsResponseDto(entity);
    }

    //겹치는 부분 리팩토링
    public Posts findPostsById(Long id){
        return postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id = "+ id));
    }
}