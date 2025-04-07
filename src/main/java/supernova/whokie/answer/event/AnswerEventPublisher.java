package supernova.whokie.answer.event;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import supernova.whokie.alarm.event.AlarmEventDto;
import supernova.whokie.answer.constants.AnswerConstants;
import supernova.whokie.pointrecord.PointRecordOption;
import supernova.whokie.pointrecord.constants.PointConstants;
import supernova.whokie.pointrecord.event.PointRecordEventDto;
import supernova.whokie.question.Question;
import supernova.whokie.ranking.event.RankingEventDto;

@Service
@RequiredArgsConstructor
public class AnswerEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public void publishAnswerAfterEvents(Long userId, Long pickedId, Question question) {
        // Ranking Count 증가
        RankingEventDto.Increase rankingEvent = RankingEventDto.Increase.toDto(pickedId, question);
        eventPublisher.publishEvent(rankingEvent);

        // 웹 알림 전송
        AlarmEventDto.Alarm alarmEvent = AlarmEventDto.Alarm.toDto(pickedId, question.getContent());
        eventPublisher.publishEvent(alarmEvent);

        // 포인트 기록
        PointRecordEventDto.Earn pointEvent = PointRecordEventDto.Earn.toDto(userId,
            AnswerConstants.ANSWER_POINT, 0,
            PointRecordOption.EARN, PointConstants.POINT_EARN_MESSAGE);
        eventPublisher.publishEvent(pointEvent);
    }
}
