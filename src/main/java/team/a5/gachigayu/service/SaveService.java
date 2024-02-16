package team.a5.gachigayu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.a5.gachigayu.domain.Promenade;
import team.a5.gachigayu.domain.Save;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.repository.PromenadeRepository;
import team.a5.gachigayu.repository.SaveRepository;

@Slf4j
@Transactional
@Service
public class SaveService {

    private final PromenadeRepository promenadeRepository;
    private final SaveRepository saveRepository;

    public SaveService(PromenadeRepository promenadeRepository, SaveRepository saveRepository) {
        this.promenadeRepository = promenadeRepository;
        this.saveRepository = saveRepository;
    }

    public void changeSavePromenade(Long promenadeId) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();

        Promenade promenade = promenadeRepository.findById(promenadeId)
                .orElseThrow();
        Save save = new Save(authenticatedUser, promenade);
        saveRepository.save(save);
    }
}
