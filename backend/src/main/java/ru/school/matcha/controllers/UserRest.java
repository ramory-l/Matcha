package ru.school.matcha.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.UserDto;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.CollectionConversionService;
import ru.school.matcha.services.interfaces.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserRest {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CollectionConversionService conversionService;

    @Autowired
    public UserRest(UserService userService, CollectionConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDto> result = conversionService.convertCollection(users, UserDto.class, Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (MatchaException ex) {
            logger.error("Failed to get all users", ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to get all users", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id).orElseThrow(() -> new MatchaException("Failed to get user by id: " + id));
            UserDto result = conversionService.convert(user, UserDto.class);
            return ResponseEntity.ok(result);
        } catch (MatchaException ex) {
            logger.error("Failed to get user by id: {}", id, ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to get user by id: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable("username") String username) {
        try {
            User user = userService.getUserByUsername(username).orElseThrow(() -> new MatchaException("Failed to get user by username: " + username));
            UserDto result = conversionService.convert(user, UserDto.class);
            return ResponseEntity.ok(result);
        } catch (MatchaException ex) {
            logger.error("Failed to get user by username: {}", username, ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to get user by username: {}", username, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        try {
            User user = conversionService.convert(userDto, User.class);
            userService.createUser(user);
            return ResponseEntity.ok().build();
        } catch (MatchaException ex) {
            logger.error("Failed to create user", ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to create user", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody UserDto userDto) {
        try {
            User user = conversionService.convert(userDto, User.class);
            userService.updateUser(user);
            return ResponseEntity.ok().build();
        } catch (MatchaException ex) {
            logger.error("Failed to update user", ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to update user", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable("id") Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        } catch (MatchaException ex) {
            logger.error("Failed to delete user by id: {}", id, ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to delete user by id: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Object> deleteUserByUsername(@PathVariable("username") String username) {
        try {
            userService.deleteUserByUsername(username);
            return ResponseEntity.ok().build();
        } catch (MatchaException ex) {
            logger.error("Failed to delete user by username: {}", username, ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to delete user by username: {}", username, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
