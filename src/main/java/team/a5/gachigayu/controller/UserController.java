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
import team.a5.gachigayu.controller.dto.response.ActivityHistoryListResponse;
import team.a5.gachigayu.controller.dto.response.UserInfoResponse;
import team.a5.gachigayu.service.ActivityService;
import team.a5.gachigayu.service.UserInfoRegistrant;

@Slf4j
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserInfoRegistrant userInfoRegistrant;
    private final ActivityService activityService;

    public UserController(UserInfoRegistrant userInfoRegistrant, ActivityService activityService) {
        this.userInfoRegistrant = userInfoRegistrant;
        this.activityService = activityService;
    }

    @GetMapping
    public UserInfoResponse userInfo() {
        return userInfoRegistrant.getUserInfo();
    }

    @PostMapping
    public void registerUserInfo(
            @RequestPart(name = "profileImage", required = false) MultipartFile profileImage,
            @ModelAttribute UserInfoRequest userInfoRequest
    ) {
        userInfoRegistrant.register(userInfoRequest, profileImage);
    }

    @GetMapping("/activities")
    public ActivityHistoryListResponse userActivityHistory() {
        return activityService.getUserActivityHistory();
    }
}
