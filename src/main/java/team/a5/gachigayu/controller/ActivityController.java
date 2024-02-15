package team.a5.gachigayu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/activities")
@RestController
public class ActivityController {

    @PostMapping
    public void startActivity() {

    }
}
