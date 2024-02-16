package team.a5.gachigayu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.a5.gachigayu.controller.dto.request.UserInfoRequest;
import team.a5.gachigayu.controller.dto.response.UserInfoResponse;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.domain.UserRecord;
import team.a5.gachigayu.repository.UserRepository;

@Slf4j
@Transactional
@Service
public class UserInfoRegistrant {

    private final UserRepository userRepository;
    private final ImageService imageService;
    private final UserRecordService userRecordService;

    public UserInfoRegistrant(UserRepository userRepository, ImageService imageService, UserRecordService userRecordService) {
        this.userRepository = userRepository;
        this.imageService = imageService;
        this.userRecordService = userRecordService;
    }

    public void register(UserInfoRequest userInfoRequest, MultipartFile profileImage) {
        String imageURL = imageService.uploadImage(profileImage);

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        authenticatedUser.updateInfo(userInfoRequest.name(), userInfoRequest.accountId(), imageURL);
        userRepository.save(authenticatedUser);
    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        UserRecord userRecord = userRecordService.getUserRecord(authenticatedUser);
        return UserInfoResponse.of(authenticatedUser, userRecord);
    }
}
