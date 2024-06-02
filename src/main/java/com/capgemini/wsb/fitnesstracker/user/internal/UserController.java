package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import com.capgemini.wsb.fitnesstracker.user.api.UserDto;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final TrainingServiceImpl trainingService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/simple")
    public List<BasicUserDto> getAllBasicUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::basicToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getSingleUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        User user = userMapper.toEntity(userDto);
        return userService.createUser(user);
    }

    @DeleteMapping("/{userid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long userid) {
        trainingService.deleteTrainingByUserId(userid);
        userService.deleteUser(userid);
    }

    @GetMapping("/email")
    public List<BasicUserDtoEmail> getUsersByPartialEmail(@RequestParam String email) {
        return userService.getUserByPartOfEmail(email)
                .stream()
                .map(userMapper::basicToDtoEmail)
                .toList();
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getUserOlderThan(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate time) {
        return userService.searchUsersOlderThan(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }
}