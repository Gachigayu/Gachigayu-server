package team.a5.gachigayu.util;

import org.springframework.stereotype.Component;
import team.a5.gachigayu.domain.value.GeoPoint;

@Component
public class GeographicalDistanceUtils {

    /**
     * @Unit: Meter
     */
    public static double calculateDistance(GeoPoint coordinateA, GeoPoint coordinateB) {
        int earthRadius = 6371;

        double latitudeA = coordinateA.getLatitude();
        double latitudeB = coordinateB.getLatitude();
        double deltaLatitude = Math.toRadians(latitudeB - latitudeA);
        double deltaLongitude = Math.toRadians(coordinateB.getLongitude() - coordinateA.getLongitude());
        double a = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2) +
                Math.cos(Math.toRadians(latitudeB)) * Math.cos(Math.toRadians(latitudeB)) *
                        Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        return distance * 1000;
    }
}
