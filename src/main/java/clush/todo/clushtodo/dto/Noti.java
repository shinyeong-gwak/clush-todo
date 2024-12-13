package clush.todo.clushtodo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data@RequiredArgsConstructor@AllArgsConstructor
public class Noti {
    @Nullable
    String tag;
    @NonNull
    String deco;
    @NonNull
    String content;


}
