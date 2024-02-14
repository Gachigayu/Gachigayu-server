package team.a5.gachigayu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.a5.gachigayu.domain.Promenade;

import java.util.List;

public interface PromenadeRepository extends JpaRepository<Promenade, Long> {

    @Query(value = "SELECT * FROM Promenade p WHERE ST_DISTANCE_SPHERE(p.location, ST_POINTFROMTEXT(:location, 4326)) <= :distance", nativeQuery = true)
    List<Promenade> findByLocationNear(String location, double distance);

    @Query(value = "SELECT * FROM Promenade p WHERE p.type = :type AND ST_DISTANCE_SPHERE(p.location, ST_POINTFROMTEXT(:location, 4326)) <= :distance", nativeQuery = true)
    List<Promenade> findByTypeAndLocationNear(String type, String location, double distance);
}
