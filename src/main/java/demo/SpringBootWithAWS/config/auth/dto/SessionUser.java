package demo.SpringBootWithAWS.config.auth.dto;

import demo.SpringBootWithAWS.domain.user.User;
import lombok.Getter;

//세션에 사용자 정보를 저장하기 위한 DTO
//USER를 사용하지 않음 -> why? USER 사용 시 직렬화 구현 에러(엔티티이기 때문)
@Getter
public class SessionUser {
    private final String name;
    private final String email;
    private final String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
