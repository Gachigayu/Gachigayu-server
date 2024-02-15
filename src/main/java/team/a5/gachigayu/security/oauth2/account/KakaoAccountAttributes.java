package team.a5.gachigayu.security.oauth2.account;

import lombok.Builder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Builder
public record KakaoAccountAttributes(String id, String email)
        implements AccountAttributes {

    public static KakaoAccountAttributes from(OAuth2User oAuth2User) {
        String id = String.valueOf((Long) oAuth2User.getAttribute("id"));
        Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
        assert kakaoAccount != null;
        String email = (String) kakaoAccount.get("email");

        return KakaoAccountAttributes.builder()
                .id(id)
                .email(email)
                .build();
    }
}
