package chatting.app.chat.member.repository;

import chatting.app.chat.entity.Friend;
import chatting.app.chat.entity.Member;
import chatting.app.chat.member.service.domain.MemberLoginForm;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
        Friend friend = new Friend(member, member);
        em.persist(friend);
    }

    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    public void remove(Long id) {
        Member member = em.find(Member.class, id);
        em.remove(member);
    }

    public boolean userIdUniqueCheck(String userId) {
        Member member = em.createQuery("select m from Member m where m.userId=:userId", Member.class)
                .setParameter("userId", userId)
                .getResultList().stream().findAny().orElse(null);

        if (member == null)
            return true;

        return false;
    }

    public Member login(MemberLoginForm form) {
        return em.createQuery("select m from Member m where userId=:userId and password = :password", Member.class)
                .setParameter("userId", form.getUserId())
                .setParameter("password", form.getPassword())
                .getResultList().stream().findAny().orElse(null);
    }

    public Member findByUserId(String userId) {
        return em.createQuery("select m from Member m where userId = :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList().stream().findAny().orElse(null);
    }

    public List<Member> myFriends(Long memberId) {
        List<Friend> friends = em.createQuery("select f from Friend f where member1.id=:memberId or member2.id=:memberId", Friend.class)
                .setParameter("memberId", memberId)
                .getResultList();

        List<Member> friendsList = new ArrayList<>();
        for (Friend friend : friends) {
            if (friend.getMember1().getId().equals(memberId))
                friendsList.add(friend.getMember2());
            else
                friendsList.add(friend.getMember1());
        }

        return friendsList;
    }
}
