package supernova.whokie.ranking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import supernova.whokie.answer.event.AnswerEventDto;
import supernova.whokie.global.constants.MessageConstants;
import supernova.whokie.global.exception.EntityNotFoundException;
import supernova.whokie.group.Groups;
import supernova.whokie.group.service.GroupReaderService;
import supernova.whokie.groupmember.service.GroupMemberReaderService;
import supernova.whokie.ranking.Ranking;
import supernova.whokie.ranking.service.dto.RankingModel;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingReaderService rankingReaderService;
    private final RankingWriterService rankingWriterService;
    private final GroupMemberReaderService groupMemberReaderService;
    private final GroupReaderService groupReaderService;

    @Transactional(readOnly = true)
    public List<RankingModel.Rank> getUserRanking(Long userId) {
        List<Ranking> entities = rankingReaderService.getTop3RankingByUserId(userId);
        return IntStream.range(0, entities.size())
            .mapToObj(i -> RankingModel.Rank.from(entities.get(i), i + 1))
            .toList();
    }

    @Transactional(readOnly = true)
    public List<RankingModel.GroupRank> getGroupRanking(Long userId, Long groupId) {
        if (!groupMemberReaderService.isGroupMemberExist(userId, groupId)) {
            throw new EntityNotFoundException(MessageConstants.GROUP_MEMBER_NOT_FOUND_MESSAGE);
        }
        RankingModel.Top3RankingEntries mapEntries = rankingReaderService.getTop3UsersFromGroupByGroupId(
            groupId);

        return IntStream.range(0, mapEntries.entries().size())
            .mapToObj(i -> RankingModel.GroupRank.from(mapEntries.entries().get(i), i + 1))
            .toList();
    }

    public void increaseRanking(AnswerEventDto.AnswerAfterEvents event) {
        Long pickedId = event.pickedId();
        String content = event.question();
        Groups group = groupReaderService.getGroupById(event.groupId());
        rankingWriterService.increaseRankingCountByUserAndQuestionAndGroups(pickedId,
            content, group);
    }

}
