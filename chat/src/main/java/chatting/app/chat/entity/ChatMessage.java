package chatting.app.chat.entity;

import jakarta.persistence.*;

import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @OneToMany(mappedBy = "chatMessage")
    private List<ChatMessageMember> receiver;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
}
