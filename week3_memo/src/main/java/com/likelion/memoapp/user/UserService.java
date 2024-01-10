package com.likelion.memoapp.user;

import com.likelion.memoapp.memo.MemoRepository;
import com.likelion.memoapp.user.dto.RequestDTO;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final MemoRepository memoRepository;

    @Autowired
    public UserService(UserRepository userRepository, MemoRepository memoRepository) {
        this.userRepository = userRepository;
        this.memoRepository = memoRepository;
    }

    public void addUser(RequestDTO requestDTO) {
        User user = new User(requestDTO.getName());
        this.userRepository.save(user);
        this.userRepository.flush();
    }

    public User getUserById(UUID id) {
        return this.userRepository.findById(id).orElseThrow();
    }

    public void deleteUserById(UUID id) {
        this.userRepository.deleteById(id);
    }

    public void updateUserById(UUID id, RequestDTO requestDTO) {
        User userToUpdate = this.userRepository.findById(id).orElseThrow();
        userToUpdate.setName(requestDTO.getName());
        userToUpdate.setUpdatedAt(new Date());
        this.userRepository.save(userToUpdate);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
