package team.a5.gachigayu.controller.dto.response;

import org.locationtech.jts.geom.Point;
import team.a5.gachigayu.domain.Route;

public record RouteResponse(int sequence, double latitude, double longitude, String description) {

    public static RouteResponse of(Route route) {
        int sequence = route.getSequence();
        Point coordinate = route.getCoordinate();
        return new RouteResponse(sequence, coordinate.getY(), coordinate.getX(), route.getDescription());
    }
}
