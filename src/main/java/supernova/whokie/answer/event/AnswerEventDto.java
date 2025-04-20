package supernova.whokie.answer.event;

import lombok.Builder;
import supernova.whokie.pointrecord.PointRecordOption;
import supernova.whokie.question.Question;

public class AnswerEventDto {

    @Builder
    public record AnswerAfterEvents(
        Long pickerId,
        Long pickedId,
        String question,
        Long groupId,
        int point,
        int amount,
        PointRecordOption option,
        String message

    ) {

        public static AnswerEventDto.AnswerAfterEvents toDto(Long pickerId, Long pickedId,
            Question question, int point, int amount, PointRecordOption option,
            String message) {
            return AnswerAfterEvents.builder()
                .pickerId(pickerId)
                .pickedId(pickedId)
                .question(question.getContent())
                .groupId(question.getGroupId())
                .point(point)
                .amount(amount)
                .option(option)
                .message(message).build();
        }
    }


}
