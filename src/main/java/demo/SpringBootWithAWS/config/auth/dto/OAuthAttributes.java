package demo.SpringBootWithAWS.config.auth.dto;

import demo.SpringBootWithAWS.domain.user.Role;
import demo.SpringBootWithAWS.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) return ofNaver("id", attributes);
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email")) //대소문자 주의...
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes){
        //제대로 email이 넘어오는지 로그 확인 : 이상 무
        System.out.println("attributes: " + attributes);
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        System.out.println("response: " + response);
        //이후 캐스팅에서 email이 전달되는지 확인 : 이상 무
        String email = (String) response.get("email");
        System.out.println("email = " + email);

        //naver가 attribute에서 email을 가져오면 계속해서 null을 가져옴
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    //가입 시 ROLE을 USER로 설정.
    public User toEntity() {
         return User.builder()
                .name(name).email(email).picture(picture).role(Role.USER).build();
    }

}
