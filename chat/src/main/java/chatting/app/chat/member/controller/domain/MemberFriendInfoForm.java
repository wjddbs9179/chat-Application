package chatting.app.chat.member.controller.domain;

import lombok.Data;

@Data
public class MemberFriendInfoForm {
    private Long id;
    private String userId;
    private String username;

    public MemberFriendInfoForm(Long id,String userId, String username) {
        this.id = id;
        this.userId = userId;
        this.username = username;
    }
}
