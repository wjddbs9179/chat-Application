package chatting.app.chat.member.repository;

import chatting.app.chat.entity.Friend;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendRepository {

    private final EntityManager em;

    public boolean save(Friend friend){
        Friend findFriend = em.createQuery("select f from Friend f where (member1=:member1 and member2=:member2) or (member2=:member1 and member1=:member2)",Friend.class)
                .setParameter("member1", friend.getMember1())
                .setParameter("member2", friend.getMember2())
                .getResultList().stream().findAny().orElse(null);

        if(findFriend==null) {
            em.persist(friend);
            return true;
        }else{
            return false;
        }
    }
}
