package chatting.app.chat.entity;

import jakarta.persistence.*;

import static jakarta.persistence.FetchType.*;

@Entity
public class ChatMessageMember {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chatMessage_id")
    private ChatMessage chatMessage;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    public ChatMessageMember() {
    }

    public ChatMessageMember(ChatMessage chatMessage, Member receiver) {
        this.chatMessage = chatMessage;
        this.receiver = receiver;
    }
}
