package supernova.whokie.ranking.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import supernova.whokie.answer.event.AnswerEventDto;
import supernova.whokie.ranking.service.RankingService;

@Slf4j
@Component
@RequiredArgsConstructor
public class RankingEventHandler {

    private final RankingService rankingService;

    @EventListener
    public void increaseRankingListener(AnswerEventDto.AnswerAfterEvents event) {
        rankingService.increaseRanking(event);
    }

}
