package team.a5.gachigayu.controller.dto.response;

import org.locationtech.jts.geom.Point;

public record Coordinate(double latitude, double longitude) {

    public static Coordinate from(Point point) {
        return new Coordinate(point.getX(), point.getY());
    }
}
