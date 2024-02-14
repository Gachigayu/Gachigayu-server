package team.a5.gachigayu.controller.dto.response;

import lombok.Builder;
import team.a5.gachigayu.domain.Promenade;
import team.a5.gachigayu.domain.value.GeoPoint;
import team.a5.gachigayu.domain.value.PromenadeType;
import team.a5.gachigayu.util.GeographicalDistanceUtils;

import java.time.LocalDateTime;

@Builder
public record PromenadeResponse(
        Long id, String title, String place, LocalDateTime startAt, int distance,
        int length, int time, PromenadeType type, boolean saved, Coordinate location
) {

    public static PromenadeResponse of(Promenade promenade, GeoPoint coordinate) {
        int distance = (int) GeographicalDistanceUtils.calculateDistance(GeoPoint.from(promenade.getLocation()), coordinate);
        return PromenadeResponse.builder()
                .id(promenade.getId())
                .title(promenade.getTitle())
                .place(promenade.getPlace())
                .startAt(promenade.getStartAt())
                .distance(distance)
                .length(promenade.getLength())
                .time(promenade.getTime())
                .type(promenade.getType())
                .saved(false) //TODO implement
                .location(Coordinate.from(promenade.getLocation()))
                .build();
    }
}