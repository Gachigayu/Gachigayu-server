package team.a5.gachigayu.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.a5.gachigayu.domain.value.SignUpResult;

import java.io.IOException;

@RequestMapping("/oauth2/authorization")
@RestController
public class OAuthController {

    @GetMapping
    public void oauth2Redirect(
            @RequestParam String accessToken, @RequestParam String refreshToken,
            @RequestParam SignUpResult account, HttpServletResponse response
    ) throws IOException {
        response.sendRedirect("/oauth2/authorization");
    }
}
