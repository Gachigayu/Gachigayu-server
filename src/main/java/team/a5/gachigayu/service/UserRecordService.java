package team.a5.gachigayu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.domain.UserRecord;
import team.a5.gachigayu.repository.UserRepository;

import java.util.List;

@Slf4j
@Transactional
@Service
public class UserRecordService {

    private final UserRepository userRepository;

    public UserRecordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRecord getUserRecord(User user) {
        int totalLength = user.getTotalLength();
        int totalTime = user.getTotalTime();

        List<User> users = userRepository.findAll(Sort.by(Direction.DESC, "totalLength"));
        double ranking = users.indexOf(user) + 1;
        int usersCount = users.size();

        return UserRecord.builder()
                .weekLength(totalLength)
                .weekTime(totalTime)
                .top((int) ((ranking / usersCount) * 100))
                .build();
    }
}
