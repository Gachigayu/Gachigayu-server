package team.a5.gachigayu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.a5.gachigayu.controller.dto.response.FeedListResponse;
import team.a5.gachigayu.domain.value.FeedOrder;
import team.a5.gachigayu.service.FeedService;

@RequestMapping("/api/feeds")
@RestController
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping
    public FeedListResponse feedList(@RequestParam FeedOrder order) {
        return feedService.getFeeds(order);
    }
}
