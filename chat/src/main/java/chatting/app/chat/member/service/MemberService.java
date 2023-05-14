package chatting.app.chat.member.service;

import chatting.app.chat.entity.Member;
import chatting.app.chat.member.repository.MemberRepository;
import chatting.app.chat.member.service.domain.MemberJoinForm;
import chatting.app.chat.member.service.domain.MemberLoginForm;
import chatting.app.chat.member.service.domain.MemberUpdateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public void join(MemberJoinForm form){

        Member member = new Member(form.getUserId(),form.getPassword(),form.getUsername());

        memberRepository.save(member);
    }

    public void update(Long id, MemberUpdateForm form){

        Member member = memberRepository.findById(id);

        member.update(form);
    }

    public void delete(Long id){
        memberRepository.remove(id);
    }

    public boolean userIdUniqueCheck(String userId) {
        return memberRepository.userIdUniqueCheck(userId);
    }

    public Member loginCheck(MemberLoginForm form) {
        return memberRepository.login(form);
    }

    public Member friendSearch(String userId) {
        return memberRepository.findByUserId(userId);
    }
}
