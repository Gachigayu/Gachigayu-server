package team.a5.gachigayu.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import org.springframework.data.geo.Point;
import team.a5.gachigayu.domain.value.PromenadeType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Promenade extends BaseEntity {

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "place", nullable = false, length = 100)
    private String place;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "length", nullable = false)
    private int length;

    @Column(name = "time", nullable = false)
    private int time;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PromenadeType type;

    @Column(name = "location", nullable = false)
    private Point location;

    @OneToMany(mappedBy = "promenade", cascade = CascadeType.REMOVE)
    private List<Route> routes = new ArrayList<>();
}
