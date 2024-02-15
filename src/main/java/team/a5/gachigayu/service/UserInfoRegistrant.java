package team.a5.gachigayu.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.a5.gachigayu.controller.dto.request.UserInfoRequest;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.repository.UserRepository;

@Transactional
@Service
public class UserInfoRegistrant {

    private final UserRepository userRepository;

    public UserInfoRegistrant(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserInfoRequest userInfoRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        authenticatedUser.updateInfo(userInfoRequest.name(), userInfoRequest.accountId());
        userRepository.save(authenticatedUser);
    }
}
