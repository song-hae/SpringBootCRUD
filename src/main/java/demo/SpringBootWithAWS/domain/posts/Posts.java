package demo.SpringBootWithAWS.domain.posts;

//domain 패키지는 게시글, 댓글, 회원, 정산 등 소프트웨어에 대한 요구사항, 문제 영역
import demo.SpringBootWithAWS.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter //Setter는? A. Setter 무작정 생성 시, 해당 클래스의 인스턴스 값들이 언제 어디서 변해야 하는 어려워짐
//Entity 클래스에는 Setter 생성 x
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity { //엔티티

    @Id //테이블의 PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //strategy의 기본 값은 Auto, PK 생성 규칙 지정자, IDENTITY를 통해 Auto Increment 설정
    //AUTO는 DB 방언에 따라 자동 선택, SEQUENCE는 oracle, postgreSQL에 유용
    private Long id;
    //웬만하면 PK 설정 시 Long 타입의 AutoIncrement 추천, 복합키로 PK 설정 시 문제 발생 가능

    @Column(length = 500, nullable = false) //문자열의 길이 기본값은 255, length로 설정, null이 들어갈 수 없도록
    private String title; //Post 제목

    @Column(length = 500, nullable = false)
    private String content; //Post 내용

    @Column(columnDefinition = "TEXT", nullable = false) //columnDefinition은 테이블 컬럼에 대해 넣을 타입, 형식을 직접 지정
    private String author;

    @Builder //Builder 패턴 사용, 생성 시점에 값을 채워주는 생성자와 역할이 같음
    //하지만 Builder를 사용하면 어느 필드에 어떤 값을 채워야 할지 명확히 인식 가능 (적극 권장)
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
