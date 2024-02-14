package team.a5.gachigayu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.a5.gachigayu.controller.dto.response.PromenadeListResponse;
import team.a5.gachigayu.controller.dto.response.PromenadeResponse;
import team.a5.gachigayu.domain.Promenade;
import team.a5.gachigayu.domain.value.GeoPoint;
import team.a5.gachigayu.domain.value.PromenadeType;
import team.a5.gachigayu.repository.PromenadeRepository;

import java.util.List;

@Slf4j
@Transactional
@Service
public class PromenadeService {

    private static final int NEARBY_DISTANCE_METER = 1_000;

    private final PromenadeRepository promenadeRepository;

    public PromenadeService(PromenadeRepository promenadeRepository) {
        this.promenadeRepository = promenadeRepository;
    }

    public PromenadeListResponse getNearbyPromenades(GeoPoint coordinate) {
        System.out.println("PromenadeService.getNearbyPromenades");
        List<Promenade> nearbyPromenades =
                promenadeRepository.findByLocationNear(coordinate.toString(), NEARBY_DISTANCE_METER);

        return convertToResponse(coordinate, nearbyPromenades);
    }

    public PromenadeListResponse getNearbyPromenadesOfType(PromenadeType type, GeoPoint coordinate) {
        System.out.println("PromenadeService.getNearbyPromenadesOfType");
        List<Promenade> nearbyPromenades =
                promenadeRepository.findByTypeAndLocationNear(type.name(), coordinate.toString(), NEARBY_DISTANCE_METER);

        return convertToResponse(coordinate, nearbyPromenades);
    }

    private static PromenadeListResponse convertToResponse(GeoPoint coordinate, List<Promenade> nearbyPromenades) {
        List<PromenadeResponse> promenades = nearbyPromenades.stream()
                .map(promenade -> PromenadeResponse.of(promenade, coordinate))
                .toList();
        return new PromenadeListResponse(promenades);
    }
}
