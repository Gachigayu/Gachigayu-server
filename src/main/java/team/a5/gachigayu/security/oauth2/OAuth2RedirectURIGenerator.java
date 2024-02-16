package team.a5.gachigayu.security.oauth2;

import org.springframework.web.util.UriComponentsBuilder;
import team.a5.gachigayu.domain.value.SignUpResult;

public class OAuth2RedirectURIGenerator {

    private static final String AUTH_REDIRECT_URL = "https://gachigayu.vercel.app/oauth2/authorization";

    public static String generateRedirectURI(String accessToken, String refreshToken, SignUpResult signUpResult) {
        return UriComponentsBuilder.fromUriString(AUTH_REDIRECT_URL)
                .queryParam("access_token", accessToken)
                .queryParam("refresh_token", refreshToken)
                .queryParam("account", signUpResult)
                .build()
                .toUriString();
    }
}
