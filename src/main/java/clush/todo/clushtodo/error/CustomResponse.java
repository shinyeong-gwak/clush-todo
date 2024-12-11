package clush.todo.clushtodo.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomResponse {
    UNAUTHORIZED(401, "로그인되지 않았어요."),
    NOT_FOUND(404,"찾는 데이터가 없어요."),
    INVALID_CREDENTIALS(401, "아이디/비밀번호를 확인하세요.");
    int code;
    String message;

}
