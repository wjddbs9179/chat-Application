package chatting.app.chat.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Friend {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member1_id")
    private Member member1;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member2_id")
    private Member member2;

    private boolean accept;

    public Friend() {
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public Friend(Member member1, Member member2) {
        this.member1 = member1;
        this.member2 = member2;
    }
}
