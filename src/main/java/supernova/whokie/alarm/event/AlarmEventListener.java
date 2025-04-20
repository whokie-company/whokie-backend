package supernova.whokie.alarm.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import supernova.whokie.alarm.service.AlarmService;
import supernova.whokie.answer.event.AnswerEventDto;

@Component
@RequiredArgsConstructor
public class AlarmEventListener {

    private final AlarmService alarmService;

    @Async
    @EventListener
    public void alarmListener(AlarmEventDto.Alarm event) {
        alarmService.alarmToConnectedUser(event);
    }

    @Async
    @EventListener
    public void alarmListener(AnswerEventDto.AnswerAfterEvents event) {
        alarmService.alarmToConnectedUser(event);
    }
}
