package clush.todo.clushtodo.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomResponse {
    UNAUTHORIZED(401, "로그인되지 않았어요."),
    NOT_FOUND(404,"찾는 데이터가 없어요."),
    DATA_NOT_SAVED(404,"데이터를 저장하지 못했어요."),
    INVALID_CREDENTIALS(401, "아이디/비밀번호를 확인하세요."),
    BAD_REQUEST(400, "구성이 잘못된 요청입니다");
    int code;
    String message;

}
