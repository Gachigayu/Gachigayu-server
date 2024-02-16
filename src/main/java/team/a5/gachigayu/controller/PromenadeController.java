package team.a5.gachigayu.controller;

import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.a5.gachigayu.controller.dto.response.PromenadeListResponse;
import team.a5.gachigayu.controller.dto.response.PromenadeRoutesResponse;
import team.a5.gachigayu.domain.value.GeoPoint;
import team.a5.gachigayu.domain.value.PromenadeType;
import team.a5.gachigayu.service.PromenadeService;

@RequestMapping("/api/promenades")
@RestController
public class PromenadeController {

    private final PromenadeService promenadeService;

    public PromenadeController(PromenadeService promenadeService) {
        this.promenadeService = promenadeService;
    }

    @GetMapping
    public PromenadeListResponse nearbyPromenadesWithFiltering(
            @RequestParam(name = "type", required = false) PromenadeType type,
            @RequestParam(name = "coordinate") Point coordinate
    ) {
        if (type == null) {
            return promenadeService.getNearbyPromenades(GeoPoint.from(coordinate));
        }

        return promenadeService.getNearbyPromenadesOfType(type, GeoPoint.from(coordinate));
    }

    @GetMapping("/{id}/routes")
    public PromenadeRoutesResponse promenadeRoutes(@PathVariable("id") Long id) {
        return promenadeService.getPromenadeRoutes(id);
    }
}
