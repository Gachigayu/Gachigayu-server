package team.a5.gachigayu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.a5.gachigayu.controller.dto.response.ActivityHistoryListResponse;
import team.a5.gachigayu.controller.dto.response.ActivityHistoryResponse;
import team.a5.gachigayu.domain.Activity;
import team.a5.gachigayu.domain.Promenade;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.domain.value.ActivityStatus;
import team.a5.gachigayu.domain.value.PromenadeType;
import team.a5.gachigayu.exception.BusinessException;
import team.a5.gachigayu.repository.ActivityRepository;
import team.a5.gachigayu.repository.PromenadeRepository;
import team.a5.gachigayu.repository.UserRepository;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Transactional
@Service
public class ActivityService {

    private final PromenadeRepository promenadeRepository;
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityService(PromenadeRepository promenadeRepository, ActivityRepository activityRepository, UserRepository userRepository) {
        this.promenadeRepository = promenadeRepository;
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
    }

    public boolean startActivity(Long promenadeId) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        Promenade promenade = promenadeRepository.findById(promenadeId)
                .orElseThrow();
        if (promenade.getType().equals(PromenadeType.TOURISM)) {
            verifyUserCertificate(authenticatedUser);
        }

        return false;
    }

    private void verifyUserCertificate(User user) {
        boolean certificate = user.isCertificate();
        if (!certificate) {
            throw new BusinessException("");
        }
    }

    public void finishActivity(Long promenadeId) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        Promenade promenade = promenadeRepository.findById(promenadeId)
                .orElseThrow();

        Activity activity = Activity.builder()
                .status(ActivityStatus.FINISH)
                .user(authenticatedUser)
                .promenade(promenade)
                .build();
        activityRepository.save(activity);

        authenticatedUser.updateActivity(promenade);
        userRepository.save(authenticatedUser);
    }

    public ActivityHistoryListResponse getUserActivityHistory() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        List<ActivityHistoryResponse> activities =
                activityRepository.findByUser(authenticatedUser, Sort.by(DESC, "createdAt"))
                        .stream()
                        .map(Activity::getPromenade)
                        .map(ActivityHistoryResponse::from)
                        .toList();
        return new ActivityHistoryListResponse(activities);
    }
}
