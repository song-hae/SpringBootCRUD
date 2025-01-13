package demo.SpringBootWithAWS.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum Role {
    //GUEST("ROLE_GUEST", "GUEST"), //어차피 user만 계층을 나눌 것이기에 불필요 (가입하지 않으면 anonymous가 되기 때문에)
    USER("ROLE_USER","USER");

    private final String key;
    private final String title;
}
