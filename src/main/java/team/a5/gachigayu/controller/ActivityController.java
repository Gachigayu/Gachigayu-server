package team.a5.gachigayu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.a5.gachigayu.controller.dto.request.FinishActivityRequest;
import team.a5.gachigayu.controller.dto.request.StartActivityRequest;
import team.a5.gachigayu.service.ActivityService;

@RequestMapping("/api/activities")
@RestController
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/start")
    public String startActivity(@RequestBody StartActivityRequest startActivityRequest) {
        boolean isStart = activityService.startActivity(startActivityRequest.promenadeId());
        return null;
    }

    @PostMapping("/finish")
    public void finishActivity(@RequestBody FinishActivityRequest finishActivityRequest) {

    }
}
