package chatting.app.chat.member.service.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberLoginForm {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
