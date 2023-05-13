package chatting.app.chat.member.repository;

import chatting.app.chat.entity.Member;
import chatting.app.chat.member.service.domain.MemberLoginForm;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findById(Long id){
        return em.find(Member.class,id);
    }

    public void remove(Long id){
        Member member = em.find(Member.class, id);
        em.remove(member);
    }

    public boolean userIdUniqueCheck(String userId) {
        Member member = em.createQuery("select m from Member m where m.userId=:userId", Member.class)
                .setParameter("userId", userId)
                .getResultList().stream().findAny().orElse(null);

        if(member==null)
            return true;

        return false;
    }

    public Member login(MemberLoginForm form) {
        return em.createQuery("select m from Member m where userId=:userid and password = :password",Member.class)
                .setParameter("userId",form.getUserId())
                .setParameter("password",form.getPassword())
                .getResultList().stream().findAny().orElse(null);
    }
}
