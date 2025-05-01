package supernova.whokie.friend.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import supernova.whokie.friend.controller.dto.FriendRequest;
import supernova.whokie.friend.controller.dto.FriendResponse;
import supernova.whokie.friend.service.FriendService;
import supernova.whokie.friend.service.dto.FriendModel;
import supernova.whokie.global.annotation.Authenticate;
import supernova.whokie.global.dto.GlobalResponse;

import java.util.List;

@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @PostMapping("")
    public GlobalResponse updateFriend(
            @Authenticate Long userId,
            @RequestBody @Valid FriendRequest.Add request
    ) {
        friendService.updateFriends(userId, request.toCommand());
        return GlobalResponse.builder().message("친구 목록 갱신 성공").build();
    }

    @GetMapping("")
    public FriendResponse.Infos getFriends(
            @Authenticate Long userId
    ) {
        List<FriendModel.Info> infos = friendService.getFriends(userId);
        return FriendResponse.Infos.from(infos);
    }

    @GetMapping("/group")
    public FriendResponse.Infos getAllFriendsByGroupId(
            @RequestParam(name = "group-id") @NotNull @Min(0) Long groupId,
            @Authenticate Long userId
    ) {
        List<FriendModel.Info> infos;
        if(groupId == 0) {
            infos = friendService.getKakaoFriends(userId);
        } else {
            infos = friendService.getGroupFriends(userId, groupId);
        }
        return FriendResponse.Infos.from(infos);
    }
}