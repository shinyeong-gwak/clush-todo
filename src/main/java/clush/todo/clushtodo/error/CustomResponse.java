package clush.todo.clushtodo.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomResponse {
    NOT_FOUND(404,"찾는 데이터가 없어요.");
    int code;
    String message;

}
