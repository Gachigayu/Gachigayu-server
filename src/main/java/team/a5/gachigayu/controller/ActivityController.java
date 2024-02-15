package team.a5.gachigayu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.a5.gachigayu.controller.dto.request.FinishActivityRequest;

@RequestMapping("/api/activities")
@RestController
public class ActivityController {

    @PostMapping
    public void finishActivity(@RequestBody FinishActivityRequest finishActivityRequest) {

    }
}
