package clush.todo.clushtodo.dto;

import clush.todo.clushtodo.entity.Calendar;
import lombok.Data;

@Data
public class Noti {
    String userId;
    String tag;
    String content;

    public Noti(String userId, String msg, Calendar.Color tag, String deco) {
        this.userId = userId;
        this.tag = tag.name();
        this.content = msg + "\n" + deco;
    }
}
