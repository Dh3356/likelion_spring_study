package com.likelion.memoapp.service;

import com.likelion.memoapp.model.User;
import com.likelion.memoapp.model.dto.UserRequestDTO;
import com.likelion.memoapp.repository.MemoRepository;
import com.likelion.memoapp.repository.UserRepository;
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

    public void addUser(UserRequestDTO userRequestDTO) {
        User user = new User(userRequestDTO.getName());
        this.userRepository.save(user);
        this.userRepository.flush();
    }

    public User getUserById(UUID id) {
        return this.userRepository.findById(id).orElseThrow();
    }

    public void deleteUserById(UUID id) {
        this.userRepository.deleteById(id);
    }

    public void updateUserById(UUID id, UserRequestDTO userRequestDTO) {
        User userToUpdate = this.userRepository.findById(id).orElseThrow();
        userToUpdate.setName(userRequestDTO.getName());
        userToUpdate.setUpdatedAt(new Date());
        this.userRepository.save(userToUpdate);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
