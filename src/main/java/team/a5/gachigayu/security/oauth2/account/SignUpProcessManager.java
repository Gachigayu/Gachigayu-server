package team.a5.gachigayu.security.oauth2.account;

import org.springframework.stereotype.Component;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.domain.value.SignUpResult;
import team.a5.gachigayu.repository.UserRepository;

import java.util.Optional;

@Component
public class SignUpProcessManager {

    private final UserRepository userRepository;

    public SignUpProcessManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SignUpResult processSignUp(AccountAttributes accountAttributes) {
        String email = accountAttributes.email();
        String nickname = accountAttributes.nickname();
        Optional<User> nullableUser = userRepository.findByEmail(email);
        if (nullableUser.isPresent()) {
            User user = nullableUser.get();
            user.updateNickname(nickname);
            userRepository.save(user);
            return SignUpResult.EXISTS;
        }

        User user = User.of(email, nickname);
        userRepository.save(user);
        return SignUpResult.NEW;
    }
}
