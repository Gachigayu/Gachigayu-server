package team.a5.gachigayu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.a5.gachigayu.controller.dto.request.UserInfoRequest;
import team.a5.gachigayu.service.UserInfoRegistrant;

@Slf4j
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserInfoRegistrant userInfoRegistrant;

    public UserController(UserInfoRegistrant userInfoRegistrant) {
        this.userInfoRegistrant = userInfoRegistrant;
    }

    @PostMapping
    public void registerUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        userInfoRegistrant.register(userInfoRequest);
    }
}
