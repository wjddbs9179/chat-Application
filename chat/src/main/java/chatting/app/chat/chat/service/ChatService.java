package chatting.app.chat.chat.service;

import chatting.app.chat.chat.repository.ChatRepository;
import chatting.app.chat.entity.ChatRoom;
import chatting.app.chat.entity.ChatRoomMember;
import chatting.app.chat.entity.Member;
import chatting.app.chat.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;


    public void createChatRoom(Long[] memberIds) {
        ChatRoom chatRoom = new ChatRoom();
        chatRepository.chatRoomSave(chatRoom);
        for (Long memberId : memberIds) {
            ChatRoomMember chatRoomMember = new ChatRoomMember();
            chatRoomMember.setChatRoom(chatRoom);
            Member member = memberRepository.findById(memberId);
            chatRoomMember.setMember(member);
            chatRepository.chatRoomMemberSave(chatRoomMember);
        }
    }

    public void createChatRoomOneToOne(Long memberId, Long friendId) {
        ChatRoom chatRoom = chatRepository.findOneToOneChatRoom(memberId,friendId);
    }
}
