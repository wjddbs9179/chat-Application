package chatting.app.chat.entity;

import chatting.app.chat.member.service.domain.MemberUpdateForm;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String userId;
    private String password;
    private String username;

    @OneToMany(mappedBy = "member1")
    private List<Friend> friends1 = new ArrayList<>();

    @OneToMany(mappedBy = "member2")
    private List<Friend> friends2 = new ArrayList<>();

    public Member() {
    }

    public Member(String userId, String password, String username) {
        this.userId = userId;
        this.password = password;
        this.username = username;
    }

    public void update(MemberUpdateForm form) {
        this.password = form.getPassword();
        this.username = form.getUsername();
    }
}
