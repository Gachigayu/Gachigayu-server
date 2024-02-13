package team.a5.gachigayu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.a5.gachigayu.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);
}
