package chatting.app.chat.member.repository;

import chatting.app.chat.entity.Friend;
import chatting.app.chat.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public List<Member> myFriends(Long memberId) {
        List<Friend> friends = em.createQuery("select f from Friend f where (member1.id=:memberId or member2.id=:memberId) and f.accept=true", Friend.class)
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
    public List<Member> myFriendsAcceptList(Long memberId) {
        List<Friend> friends = em.createQuery("select f from Friend f where (member1.id=:memberId or member2.id=:memberId) and f.accept=false", Friend.class)
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

    public void accept(Long memberId, Long friendId) {
        Friend findFriend = em.createQuery("select f from Friend f where (f.member1.id=:member1Id and f.member2.id=:member2Id) or (f.member2.id=:member1Id and f.member1.id=:member2Id)",Friend.class)
                .setParameter("member1Id", memberId)
                .setParameter("member2Id", friendId)
                .getResultList().stream().findAny().orElse(null);

        findFriend.setAccept(true);
    }

    public void refuse(Long memberId, Long friendId) {
        Friend findFriend = em.createQuery("select f from Friend f where (f.member1.id=:member1Id and f.member2.id=:member2Id) or (f.member2.id=:member1Id and f.member1.id=:member2Id)",Friend.class)
                .setParameter("member1Id", memberId)
                .setParameter("member2Id", friendId)
                .getResultList().stream().findAny().orElse(null);

        em.remove(findFriend);
    }
}
