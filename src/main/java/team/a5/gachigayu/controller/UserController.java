package team.a5.gachigayu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.a5.gachigayu.controller.dto.request.UserInfoRequest;
import team.a5.gachigayu.controller.dto.response.UserActivityHistoryResponse;
import team.a5.gachigayu.controller.dto.response.UserInfoResponse;
import team.a5.gachigayu.service.UserInfoRegistrant;

@Slf4j
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserInfoRegistrant userInfoRegistrant;

    public UserController(UserInfoRegistrant userInfoRegistrant) {
        this.userInfoRegistrant = userInfoRegistrant;
    }

    @GetMapping
    public UserInfoResponse userInfo() {
        return userInfoRegistrant.getUserInfo();
    }

    @PostMapping
    public void registerUserInfo(
            @RequestPart(name = "profileImage") MultipartFile profileImage,
            @ModelAttribute UserInfoRequest userInfoRequest
    ) {
        userInfoRegistrant.register(userInfoRequest, profileImage);
    }

    @GetMapping("/activities")
    public UserActivityHistoryResponse userActivityHistory() {
        return null;
    }
}
