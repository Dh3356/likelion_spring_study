package com.likelion.memoapp.user;

import com.likelion.memoapp.user.dto.RequestDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return this.userService.getUserById(UUID.fromString(id));
    }

    @PostMapping
    public void addUser(@RequestBody RequestDTO requestDTO) throws Exception {
        this.userService.addUser(requestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUseryId(@PathVariable("id") String id) {
        this.userService.deleteUserById(UUID.fromString(id));
    }

    @PatchMapping("/{id}")
    public void updateUserById(@PathVariable("id") String id, @RequestBody RequestDTO requestDTO) {
        this.userService.updateUserById(UUID.fromString(id), requestDTO);
    }
}
