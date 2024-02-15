package team.a5.gachigayu.controller.dto.response;

import lombok.Builder;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.domain.UserRecord;

@Builder
public record UserInfoResponse(
        String nickname, String name, String accountId, String profileImage,
        boolean certificated, String weekLength, String weekTime, String top
) {

    public static UserInfoResponse of(User user, UserRecord userRecord) {
        return UserInfoResponse.builder()
                .nickname(user.getNickname())
                .name(user.getName())
                .accountId(user.getAccountId())
                .profileImage(user.getProfileImage())
                .certificated(user.isCertificate())
                .weekLength(userRecord.getWeekLength())
                .weekTime(userRecord.getWeekTime())
                .top(userRecord.getTop())
                .build();
    }
}
