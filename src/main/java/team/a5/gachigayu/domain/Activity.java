package team.a5.gachigayu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import team.a5.gachigayu.domain.value.ActivityStatus;

@Getter
@Entity
public class Activity extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR")
    private ActivityStatus status;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "promenade_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Promenade promenade;

    public Activity() {
    }

    @Builder
    public Activity(ActivityStatus status, User user, Promenade promenade) {
        this.status = status;
        this.user = user;
        this.promenade = promenade;
    }
}
