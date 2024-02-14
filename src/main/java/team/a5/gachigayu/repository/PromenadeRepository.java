package team.a5.gachigayu.repository;

import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.a5.gachigayu.domain.Promenade;
import team.a5.gachigayu.domain.value.PromenadeType;

import java.util.List;

public interface PromenadeRepository extends JpaRepository<Promenade, String> {

    @Query(value = "SELECT * FROM Promenade p WHERE ST_DISTANCE_SPHERE(p.location, :location) <= :distance", nativeQuery = true)
    List<Promenade> findByLocationNear(Point location, double distance);

    @Query(value = "SELECT * FROM Promenade p WHERE p.type = :type AND ST_DISTANCE_SPHERE(p.location, :location) <= :distance", nativeQuery = true)
    List<Promenade> findByTypeAndLocationNear(PromenadeType type, Point location, double distance);
}
