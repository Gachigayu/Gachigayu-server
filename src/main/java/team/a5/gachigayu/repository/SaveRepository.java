package team.a5.gachigayu.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import team.a5.gachigayu.domain.Promenade;
import team.a5.gachigayu.domain.Save;
import team.a5.gachigayu.domain.User;

import java.util.List;
import java.util.Optional;

public interface SaveRepository extends JpaRepository<Save, Long> {

    @EntityGraph(attributePaths = "promenade")
    List<Save> findByUser(User user);

    Optional<Save> findByUserAndPromenade(User authenticatedUser, Promenade promenade);
}
