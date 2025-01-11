package demo.SpringBootWithAWS.service;

import demo.SpringBootWithAWS.domain.posts.PostsRepository;
import demo.SpringBootWithAWS.web.dto.PostsSaveRequestDto;
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
}
