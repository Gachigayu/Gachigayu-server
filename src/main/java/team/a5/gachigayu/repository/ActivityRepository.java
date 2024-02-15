package team.a5.gachigayu.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import team.a5.gachigayu.domain.Activity;
import team.a5.gachigayu.domain.User;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @EntityGraph(attributePaths = "promenade")
    List<Activity> findByUser(User user);
}
