package supernova.whokie.answer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import supernova.whokie.alarm.event.AlarmEventDto;
import supernova.whokie.answer.constants.AnswerConstants;
import supernova.whokie.answer.event.AnswerEventPublisher;
import supernova.whokie.answer.service.dto.AnswerCommand;
import supernova.whokie.global.constants.MessageConstants;
import supernova.whokie.global.exception.InvalidEntityException;
import supernova.whokie.group.Groups;
import supernova.whokie.group.service.GroupReaderService;
import supernova.whokie.pointrecord.PointRecordOption;
import supernova.whokie.pointrecord.constants.PointConstants;
import supernova.whokie.pointrecord.event.PointRecordEventDto;
import supernova.whokie.question.Question;
import supernova.whokie.question.service.QuestionReaderService;
import supernova.whokie.ranking.service.RankingWriterService;

@Service
@RequiredArgsConstructor
public class AnswerFacade {

    private final AnswerService answerService;
    private final QuestionReaderService questionReaderService;
    private final AnswerEventPublisher answerEventPublisher;


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
        answerEventPublisher.publishAnswerAfterEvents(userId, pickedId, question);
    }
}
