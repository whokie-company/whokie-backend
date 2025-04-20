package supernova.whokie.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import supernova.whokie.answer.constants.AnswerConstants;
import supernova.whokie.answer.event.AnswerEventDto;
import supernova.whokie.answer.service.dto.AnswerCommand;
import supernova.whokie.global.constants.MessageConstants;
import supernova.whokie.global.exception.InvalidEntityException;
import supernova.whokie.pointrecord.PointRecordOption;
import supernova.whokie.pointrecord.constants.PointConstants;
import supernova.whokie.question.Question;
import supernova.whokie.question.service.QuestionReaderService;

@Service
@RequiredArgsConstructor
public class AnswerFacade {

    private final AnswerService answerService;
    private final QuestionReaderService questionReaderService;
    private final ApplicationEventPublisher eventPublisher;


    public void answerToCommonQuestion(Long userId, AnswerCommand.CommonAnswer command) {
        Question question = questionReaderService.getQuestionById(command.questionId());
        answerToQuestionAndNotify(userId, command.pickedId(), question);
    }

    public void answerToGroupQuestion(Long userId, AnswerCommand.Group command) {
        Question question = questionReaderService.getQuestionById(command.questionId());
        if (question.isNotCorrectGroupQuestion(command.groupId())) {
            throw new InvalidEntityException(MessageConstants.GROUP_NOT_FOUND_MESSAGE);
        }
        answerToQuestionAndNotify(userId, command.pickedId(), question);

    }

    private void answerToQuestionAndNotify(Long userId, Long pickedId, Question question) {
        answerService.answerToQuestion(userId, pickedId, question);
        /**
         * 이벤트 발행
         * 1. 알람
         * 2. 랭킹
         * 3. 포인트
         */
        AnswerEventDto.AnswerAfterEvents answerAfterEvents = AnswerEventDto.AnswerAfterEvents.toDto(
            userId, pickedId, question,
            AnswerConstants.ANSWER_POINT, 0,
            PointRecordOption.EARN, PointConstants.POINT_EARN_MESSAGE
        );
        eventPublisher.publishEvent(answerAfterEvents);
    }
}
