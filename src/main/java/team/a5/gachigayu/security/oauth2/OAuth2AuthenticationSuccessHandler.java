package team.a5.gachigayu.security.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import team.a5.gachigayu.security.oauth2.account.AccountAttributes;
import team.a5.gachigayu.security.oauth2.account.KakaoAccountAttributes;
import team.a5.gachigayu.security.oauth2.account.SignUpProcessManager;
import team.a5.gachigayu.security.token.TokenProvider;

import java.io.IOException;

@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final SignUpProcessManager signUpProcessManager;

    public OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider, SignUpProcessManager signUpProcessManager) {
        this.tokenProvider = tokenProvider;
        this.signUpProcessManager = signUpProcessManager;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("oAuth2User = {}", oAuth2User);
        AccountAttributes kakaoAccountAttributes = KakaoAccountAttributes.from(oAuth2User);

        signUpProcessManager.processSignUp(kakaoAccountAttributes);

        String email = kakaoAccountAttributes.email();
        String accessToken = tokenProvider.issueAccessToken(email);
        String refreshToken = tokenProvider.issueRefreshToken(email);

        String redirectURI = OAuth2RedirectURIGenerator
                .generateRedirectURI(accessToken, refreshToken);
        getRedirectStrategy().sendRedirect(request, response, redirectURI);
    }
}
