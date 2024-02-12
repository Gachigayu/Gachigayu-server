package team.a5.gachigayu.security.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import team.a5.gachigayu.security.token.TokenProvider;

import java.io.IOException;
import java.util.Map;

public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    public OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
        String email = (String) kakaoAccount.get("email");

        String accessToken = tokenProvider.issueAccessToken(email);
        String refreshToken = tokenProvider.issueRefreshToken(email);

        //TODO Find users by email and redirect register page with tokens if they do not exist

        //TODO Redirect with tokens
    }
}
