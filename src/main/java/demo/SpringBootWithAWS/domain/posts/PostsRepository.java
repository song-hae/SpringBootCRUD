package demo.SpringBootWithAWS.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//DB 레이어 접근자, JPARepository<Entity 클래스, PK 타입>을 통해 기본적인 CRUD 메소드 생성
public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("select p FROM Posts p order by p.id DESC")
    List<Posts> findAllDesc();
}
