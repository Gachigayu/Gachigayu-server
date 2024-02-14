package team.a5.gachigayu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

import java.awt.*;

@Getter
@Entity
public class Route extends BaseEntity {

    @Column(name = "coordinate", nullable = false)
    private Point coordinate;

    @Column(name = "description", length = 50)
    private String description;

    @Column(name = "sequence", nullable = false, length = 50)
    private int sequence;

    @JoinColumn(name = "promenade_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Promenade promenade;
}
