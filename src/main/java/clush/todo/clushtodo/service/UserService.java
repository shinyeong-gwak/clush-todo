package clush.todo.clushtodo.service;

import clush.todo.clushtodo.entity.User;
import clush.todo.clushtodo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Autowired
    UserRepo repo;

    public Optional<User> getUser(String id) {
        return repo.findById(id);
    }
    public boolean simpleLogin(String id, String pw) {
        if(repo.existsById(id)) {
            if(repo.findByUserIdAndPassword(id,pw).isPresent())
                return true;
        }
        return false;
    }
}
