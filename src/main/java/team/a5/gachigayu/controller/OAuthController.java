package team.a5.gachigayu.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import team.a5.gachigayu.domain.value.SignUpResult;

import java.io.IOException;

@RequestMapping("/oauth2/authorization")
@RestController
public class OAuthController {

    @GetMapping
    public void oauth2Redirect(
            @RequestParam("access_token") String accessToken, @RequestParam("refresh_token") String refreshToken,
            @RequestParam SignUpResult account, HttpServletResponse response
    ) throws IOException {
        String uri = UriComponentsBuilder.fromUriString("/oauth2/authorization")
                .queryParam("access_token", accessToken)
                .queryParam("refresh_token", refreshToken)
                .queryParam("account", account)
                .build()
                .toUriString();
        response.sendRedirect(uri);
    }
}
