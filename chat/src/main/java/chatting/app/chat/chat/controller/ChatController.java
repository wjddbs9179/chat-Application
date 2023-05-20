package chatting.app.chat.chat.controller;

import chatting.app.chat.chat.service.ChatService;
import chatting.app.chat.entity.Member;
import chatting.app.chat.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat/")
@Slf4j
public class ChatController {
    private final ChatService chatService;
    private final MemberService memberService;

    @GetMapping("createChatRoom")
    public String createChatRoom(HttpSession session, Model model){
        Long memberId = (Long) session.getAttribute("memberId");
        List<Member> members = memberService.myFriendList(memberId);

        model.addAttribute("members",members);
        return "chat/create-chatRoom-form";
    }

    @PostMapping("createChatRoom")
    public String createChatRoom(@RequestParam("members")Long[] memberIds){
        for(Long memberId:memberIds){
            log.info("memberId = {}",memberId);
        }

        chatService.createChatRoom(memberIds);

        return "redirect:/";
    }

    @GetMapping("createChatRoomOneToOne/{friendId}")
    public String creteChatRoomOneToOne(HttpSession session, @PathVariable("friendId")Long friendId){
        Long memberId = (Long) session.getAttribute("memberId");

        chatService.createChatRoomOneToOne(memberId,friendId);

        return "redirect:/";
    }
}
