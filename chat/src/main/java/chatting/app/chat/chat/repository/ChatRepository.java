package chatting.app.chat.chat.repository;

import chatting.app.chat.entity.ChatRoom;
import chatting.app.chat.entity.ChatRoomMember;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private final EntityManager em;

    public void chatRoomSave(ChatRoom chatRoom){
        em.persist(chatRoom);
    }

    public void chatRoomMemberSave(ChatRoomMember chatRoomMember) {
        em.persist(chatRoomMember);
    }

    public ChatRoom findOneToOneChatRoom(Long memberId, Long friendId) {
        return em.createQuery("select crm from ChatRoomMember crm",ChatRoom.class)
                .getResultList().stream().findAny().orElse(null);
    }
}
