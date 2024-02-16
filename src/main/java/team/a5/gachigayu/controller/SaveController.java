package team.a5.gachigayu.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.a5.gachigayu.controller.dto.request.SaveRequest;
import team.a5.gachigayu.service.SaveService;

@RequestMapping("/api/saves")
@RestController
public class SaveController {

    private final SaveService saveService;

    public SaveController(SaveService saveService) {
        this.saveService = saveService;
    }

    @PostMapping
    public void savePromenade(@RequestBody SaveRequest saveRequest) {
        saveService.changeSavePromenade(saveRequest.promenadeId());
    }
}
