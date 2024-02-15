package team.a5.gachigayu.controller.dto.response;

import lombok.Builder;
import team.a5.gachigayu.domain.Promenade;
import team.a5.gachigayu.domain.value.PromenadeType;

import java.time.LocalDateTime;

@Builder
public record FeedResponse(Long id, String title, String place, LocalDateTime startAt, int length, int time,
                           PromenadeType type) {

    public static FeedResponse of(Promenade promenade) {
        return FeedResponse.builder()
                .id(promenade.getId())
                .title(promenade.getTitle())
                .place(promenade.getPlace())
                .startAt(promenade.getStartAt())
                .length(promenade.getLength())
                .time(promenade.getTime())
                .type(promenade.getType())
                .build();
    }
}
