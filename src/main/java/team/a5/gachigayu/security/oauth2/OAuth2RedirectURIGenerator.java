package team.a5.gachigayu.security.oauth2;

import org.springframework.web.util.UriComponentsBuilder;

public class OAuth2RedirectURIGenerator {

    private static final String AUTH_REDIRECT_URL = "http://localhost:3000/oauth2/authorization";

    public static String generateRedirectURI(String accessToken, String refreshToken) {
        return UriComponentsBuilder.fromUriString(AUTH_REDIRECT_URL)
                .queryParam("access_token", accessToken)
                .queryParam("refresh_token", refreshToken)
                .build()
                .toUriString();
    }
}
