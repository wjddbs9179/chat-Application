package chatting.app.chat;

import chatting.app.chat.entity.Member;
import chatting.app.chat.file.FileStore;
import chatting.app.chat.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    @Value("${file.dir}")
    private String fileDir;
    private final FileStore fileStore;

    @GetMapping("/")
    public String home(HttpSession session, Model model){
        Long memberId = (Long)session.getAttribute("memberId");
        if(memberId!=null) {
            List<Member> friends = memberService.myFriendList(memberId);
            model.addAttribute("friends", friends);
        }
        return "home";
    }

    @ResponseBody
    @GetMapping("images/{filename}")
    public Resource imageView(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }
}
