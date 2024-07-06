package com.rebook.user.controller;

import com.rebook.auth.annotation.LoginRequired;
import com.rebook.common.schema.ListResponse;
import com.rebook.user.controller.request.UserUpdateRequest;
import com.rebook.user.controller.response.UserResponse;
import com.rebook.user.service.UserService;
import com.rebook.user.service.command.UserUpdateCommand;
import com.rebook.user.service.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

//    @PostMapping
//    @Operation(summary = "Create a new user", description = "새로운 유저를 등록한다.")
//    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest user) {
//        UserResponse user = userService.createUser(user);
//        return ResponseEntity.ok(user);
//    }

    @LoginRequired
    @GetMapping
    @Operation(summary = "Get all users", description = "모든 유저를 조회한다.")
    public ResponseEntity<ListResponse<UserResponse>> getAllUsers() {
        final List<UserDto> users = userService.getAllUsers();

        List<UserResponse> userResponses = users.stream()
                .map(UserResponse::fromDto)
                .toList();

        return ResponseEntity.ok().body(new ListResponse<>(userResponses));
    }

    @LoginRequired
    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "유저 ID로 특정 유저를 조회한다.")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long userId) {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(UserResponse.fromDto(user));
    }

    @LoginRequired
    @PutMapping("/{userId}")
    @Operation(summary = "Update user by ID", description = "유저 ID로 특정 유저를 수정한다.")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest request) {

        UserDto updatedUser = userService.updateUser(UserUpdateCommand.from(request, userId));

        return ResponseEntity.ok(UserResponse.fromDto(updatedUser));
    }

    @LoginRequired
    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by ID", description = "유저 ID로 특정 유저를 삭제한다.")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId) {
        userService.softDelete(userId);
        return ResponseEntity.noContent().build();
    }
}
