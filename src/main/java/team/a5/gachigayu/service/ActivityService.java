package team.a5.gachigayu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.a5.gachigayu.domain.Promenade;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.domain.value.PromenadeType;
import team.a5.gachigayu.exception.BusinessException;
import team.a5.gachigayu.repository.PromenadeRepository;

@Slf4j
@Transactional
@Service
public class ActivityService {

    private final PromenadeRepository promenadeRepository;

    public ActivityService(PromenadeRepository promenadeRepository) {
        this.promenadeRepository = promenadeRepository;
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
}
