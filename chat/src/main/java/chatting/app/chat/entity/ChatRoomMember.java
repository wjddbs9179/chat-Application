package chatting.app.chat.entity;

import jakarta.persistence.*;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Setter
public class ChatRoomMember {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
}
