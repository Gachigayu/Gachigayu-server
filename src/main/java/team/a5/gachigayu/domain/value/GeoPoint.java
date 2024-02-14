package team.a5.gachigayu.domain.value;

import lombok.Getter;
import org.springframework.data.geo.Point;

@Getter
public class GeoPoint extends Point {

    private double latitude = super.getX();
    private double longitude = super.getY();

    public GeoPoint(double latitude, double longitude) {
        super(longitude, latitude);
    }

    public static GeoPoint of(double latitude, double longitude) {
        return new GeoPoint(latitude, longitude);
    }

    public static GeoPoint from(Point point) {
        return new GeoPoint(point.getX(), point.getY());
    }

    public static GeoPoint from(org.locationtech.jts.geom.Point point) {
        return new GeoPoint(point.getX(), point.getY());
    }

    @Override
    public String toString() {
        return "POINT(" + getLatitude() + " " + getLongitude() + ")";
    }
}
