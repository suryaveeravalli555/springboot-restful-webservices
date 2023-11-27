package com.example.springbootrestfulwebservices.controller;


import com.example.springbootrestfulwebservices.dto.UserDto;
import com.example.springbootrestfulwebservices.entity.User;
import com.example.springbootrestfulwebservices.exception.ErrorDetails;
import com.example.springbootrestfulwebservices.exception.ResourceNotFoundException;
import com.example.springbootrestfulwebservices.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@Tag(
     name = "CRUD REST API is being done here",
        description = "Create , Update , Get, GetAll, Delete"
)
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    //build create user REST API
    @Operation(
            summary = "Create User REST API",
            description = "Create User REST API is used to save user in database "
    )
    @ApiResponse(
            responseCode = "202",
            description = "HTTP status 201 created"
    )
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto user){
        UserDto savedUSer = userService.createUser(user);
        return new ResponseEntity<>(savedUSer, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get User REST API",
            description = "Get User REST API is used to get user from database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 success"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId){
        UserDto user =  userService.getUserById(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Operation(
            summary = "Get all User REST API",
            description = "Get User REST API is used to Get all the user from database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 ok"
    )
    @GetMapping()
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }


    @Operation(
            summary = "Update User REST API",
            description = "Update User REST API is used to update user in database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 Ok"
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,@RequestBody @Valid UserDto user){
        user.setId(userId);
        UserDto updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @Operation(
            summary = "Delete User REST API",
            description = "Delete User REST API is used to delete user in database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status 200 Success"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>("User successfully deleted",HttpStatus.OK);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public  ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                webRequest.getDescription(false),
//                "User Not Found"
//        );
//        return  new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//
//    }
}
