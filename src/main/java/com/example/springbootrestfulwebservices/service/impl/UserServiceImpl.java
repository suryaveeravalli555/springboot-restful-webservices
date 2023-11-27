package com.example.springbootrestfulwebservices.service.impl;

import com.example.springbootrestfulwebservices.dto.UserDto;
import com.example.springbootrestfulwebservices.entity.User;
import com.example.springbootrestfulwebservices.exception.EmailAlreadyExistsException;
import com.example.springbootrestfulwebservices.exception.ResourceNotFoundException;

import com.example.springbootrestfulwebservices.mapper.UserMapper;
import com.example.springbootrestfulwebservices.repository.UserRepository;
import com.example.springbootrestfulwebservices.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        //convert UserDto into User JPA entity
        //User user = UserMapper.mapToUser(userDto);
       User user = modelMapper.map(userDto,User.class);

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if(optionalUser.isPresent()){
            throw new EmailAlreadyExistsException("Email Already Exists for User");
        }

       // User user = AutoUserMapper.MAPPER.mapToUser(userDto);
        User savedUser = userRepository.save(user);

        //convert User JPA entity into UserDto
       // UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);


//        UserDto savedUserDto = AutoUserMapper.MAPPER.mapToUserDto(savedUser);
         return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {

       User  user = userRepository.findById(userId).orElseThrow(
               ()-> new ResourceNotFoundException("User", "id", userId)
       );
//        return UserMapper.mapToUserDto(user);
        return modelMapper.map(user,UserDto.class);


       // return AutoUserMapper.MAPPER.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
      //  return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());
       return users.stream().map((user) -> modelMapper.map(user,UserDto.class) ).collect(Collectors.toList());


      //  return users.stream().map((user) -> AutoUserMapper.MAPPER.mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user) {

        User existingUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", user.getId())
        );

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);
      //  return UserMapper.mapToUserDto(updatedUser);
        return modelMapper.map(updatedUser,UserDto.class);


      //  return AutoUserMapper.MAPPER.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );
        userRepository.deleteById(userId);
    }


}
