package team.a5.gachigayu.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.a5.gachigayu.domain.Promenade;

import java.time.LocalDateTime;
import java.util.List;

public interface PromenadeRepository extends JpaRepository<Promenade, Long> {

    @Query(value = "SELECT * FROM promenade p " +
            "WHERE DATE(p.start_at) = DATE(CONVERT_TZ(NOW(), '+00:00', '+09:00'))" +
            "AND ST_DISTANCE_SPHERE(p.location, ST_POINTFROMTEXT(:location, 4326)) <= :distance",
            nativeQuery = true)
    List<Promenade> findByLocationNear(String location, double distance);

    @Query(value = "SELECT * FROM promenade p " +
            "WHERE p.type = :type " +
            "AND DATE(p.start_at) = DATE(CONVERT_TZ(NOW(), '+00:00', '+09:00'))" +
            "AND ST_DISTANCE_SPHERE(p.location, ST_POINTFROMTEXT(:location, 4326)) <= :distance",
            nativeQuery = true)
    List<Promenade> findByTypeAndLocationNear(String type, String location, double distance);

    List<Promenade> findByStartAtAfter(LocalDateTime dateTime, Pageable pageable);
}
