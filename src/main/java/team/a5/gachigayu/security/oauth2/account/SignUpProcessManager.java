package team.a5.gachigayu.security.oauth2.account;

import org.springframework.stereotype.Component;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.repository.UserRepository;

@Component
public class SignUpProcessManager {

    private final UserRepository userRepository;

    public SignUpProcessManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void processSignUp(AccountAttributes accountAttributes) {
        String email = accountAttributes.email();
        String nickname = accountAttributes.nickname();
        User user = userRepository.findByEmail(email)
                .orElse(User.of(email, nickname));
        user.updateNickname(nickname);
        userRepository.save(user);
    }
}
