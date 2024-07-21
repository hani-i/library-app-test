package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

//컨트롤러가 객체를 직접 변환하지는 않음. 따라서 @RequestBody가 붙지 않음

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public void saveUser(UserCreateRequest request){
        userRepository.saveUser(request.getName(), request.getAge());
    }

    public List<UserResponse> getUsers(){
        return userRepository.getUsers();
    }

    public void updateUser(UserUpdateRequest request){
        if (userRepository.isUserNoExist(request.getId()))
        userRepository.updateUserName(request.getName(), request.getId());
    }

    public void deleteUser(String name){

        if (userRepository.isUserNoExist(name)){
            throw new IllegalArgumentException();
        }

        userRepository.deleteUser(name);
    }
}
