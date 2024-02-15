package team.a5.gachigayu.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.a5.gachigayu.controller.dto.response.FeedListResponse;
import team.a5.gachigayu.controller.dto.response.FeedResponse;
import team.a5.gachigayu.domain.Promenade;
import team.a5.gachigayu.domain.value.FeedOrder;
import team.a5.gachigayu.repository.PromenadeRepository;
import team.a5.gachigayu.repository.SaveRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class FeedService {

    private static final Map<FeedOrder, Function<FeedService, List<Promenade>>> orderingHandler = new HashMap<>();

    static {
        orderingHandler.put(FeedOrder.POPULARITY, FeedService::popularity);
        orderingHandler.put(FeedOrder.LATEST, FeedService::latest);
    }

    private final PromenadeRepository promenadeRepository;
    private final SaveRepository saveRepository;

    public FeedService(PromenadeRepository promenadeRepository, SaveRepository saveRepository) {
        this.promenadeRepository = promenadeRepository;
        this.saveRepository = saveRepository;
    }

    public FeedListResponse getFeeds(FeedOrder order) {
        List<FeedResponse> feedResponseList = orderingHandler.get(order)
                .apply(this)
                .stream()
                .map(FeedResponse::of)
                .toList();
        return new FeedListResponse(feedResponseList);
    }

    private List<Promenade> popularity() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        return promenadeRepository.findByStartAtAfter(LocalDateTime.now(), pageRequest)
                .stream()
                .sorted(Comparator.comparing(Promenade::getSavesCount).reversed())
                .toList();
    }

    private List<Promenade> latest() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.DESC, "createdAt");
        return promenadeRepository.findByStartAtAfter(LocalDateTime.now(), pageRequest);
    }
}
