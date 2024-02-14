package team.a5.gachigayu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.a5.gachigayu.controller.dto.response.PromenadeListResponse;
import team.a5.gachigayu.controller.dto.response.PromenadeResponse;
import team.a5.gachigayu.controller.dto.response.PromenadeRoutesResponse;
import team.a5.gachigayu.controller.dto.response.RouteResponse;
import team.a5.gachigayu.domain.Promenade;
import team.a5.gachigayu.domain.Save;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.domain.value.GeoPoint;
import team.a5.gachigayu.domain.value.PromenadeType;
import team.a5.gachigayu.repository.PromenadeRepository;
import team.a5.gachigayu.repository.SaveRepository;

import java.util.List;

@Slf4j
@Transactional
@Service
public class PromenadeService {

    private static final int NEARBY_DISTANCE_METER = 1_000;

    private final PromenadeRepository promenadeRepository;
    private final SaveRepository saveRepository;

    public PromenadeService(PromenadeRepository promenadeRepository, SaveRepository saveRepository) {
        this.promenadeRepository = promenadeRepository;
        this.saveRepository = saveRepository;
    }

    @Transactional(readOnly = true)
    public PromenadeListResponse getNearbyPromenades(GeoPoint coordinate) {
        List<Promenade> nearbyPromenades =
                promenadeRepository.findByLocationNear(coordinate.toString(), NEARBY_DISTANCE_METER);

        return convertToResponse(coordinate, nearbyPromenades);
    }

    @Transactional(readOnly = true)
    public PromenadeListResponse getNearbyPromenadesOfType(PromenadeType type, GeoPoint coordinate) {
        List<Promenade> nearbyPromenades =
                promenadeRepository.findByTypeAndLocationNear(type.name(), coordinate.toString(), NEARBY_DISTANCE_METER);

        return convertToResponse(coordinate, nearbyPromenades);
    }

    private PromenadeListResponse convertToResponse(GeoPoint coordinate, List<Promenade> nearbyPromenades) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<Promenade> savedPromenades = saveRepository.findByUser(user)
                .stream()
                .map(Save::getPromenade)
                .toList();

        List<PromenadeResponse> promenades = nearbyPromenades.stream()
                .map(promenade -> PromenadeResponse.of(promenade, coordinate, savedPromenades.contains(promenade)))
                .toList();
        return new PromenadeListResponse(promenades);
    }

    public PromenadeRoutesResponse getPromenadeRoutes(Long id) {
        Promenade promenade = promenadeRepository.findById(id)
                .orElseThrow();
        List<RouteResponse> routeResponseList = promenade.getRoutes()
                .stream()
                .map(RouteResponse::of)
                .toList();
        return new PromenadeRoutesResponse(routeResponseList);
    }
}
