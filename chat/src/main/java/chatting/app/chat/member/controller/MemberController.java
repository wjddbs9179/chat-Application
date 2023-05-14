package chatting.app.chat.member.controller;

import chatting.app.chat.entity.Member;
import chatting.app.chat.member.controller.domain.MemberFriendInfoForm;
import chatting.app.chat.member.service.MemberService;
import chatting.app.chat.member.service.domain.MemberJoinForm;
import chatting.app.chat.member.service.domain.MemberLoginForm;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("join")
    public String join(Model model){
        model.addAttribute("form",new MemberJoinForm());
        return "member/join";
    }
    @PostMapping("join")
    public String join(@Validated @ModelAttribute("form") MemberJoinForm form, BindingResult br){

        if(!form.getPassword().equals(form.getPassword2()))
            br.rejectValue("password2",null,"비밀번호와 일치하지 않습니다.");


        if(!memberService.userIdUniqueCheck(form.getUserId()))
            br.rejectValue("userId",null,"중복된 아이디 입니다.");

        if(br.hasErrors())
            return "member/join";


        memberService.join(form);

        return "member/join-success";
    }

    @GetMapping("login")
    public String login(Model model){
        model.addAttribute("form",new MemberLoginForm());
        return "member/login";
    }

    @PostMapping("login")
    public String login(@Validated @ModelAttribute("form") MemberLoginForm form, BindingResult br, HttpSession session){
        Member member = memberService.loginCheck(form);

        if(member==null)
            return "member/login-fail";

        if(br.hasErrors())
            return "member/login";

        session.setAttribute("memberId",member.getId());

        return "member/login-success";
    }

    @GetMapping("addFriend")
    public String addFriend(){
        return "member/addFriend";
    }

    @PostMapping("addFriend")
    public String addFriend(@RequestParam String userId,Model model){
        Member member = memberService.friendSearch(userId);
        if(member==null)
            return "member/not-found-friend";
        model.addAttribute("form",new MemberFriendInfoForm(member.getId(),userId,member.getUsername()));
        return "member/friend-info";
    }

    @GetMapping("addFriendExecution/{id}")
    public String addFriendExecution(HttpSession session,@PathVariable("id")Long id){
        Long memberId = (Long) session.getAttribute("memberId");
        Member member1 = memberService.memberInfo(memberId);
        Member member2 = memberService.memberInfo(id);
        if(memberService.addFriend(member1,member2)){
            return "redirect:/";
        }else{
            return "member/already-added-friend";
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
