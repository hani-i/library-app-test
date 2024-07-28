package com.group.libraryapp.controller.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;

import com.group.libraryapp.service.user.UserServiceV1;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserServiceV2 userService;
    //private final JdbcTemplate jdbcTemplate; //db에 접근 . 이제 가져올필요 없음

    //생성자를 만듦. jdbc 템플릿을 생성자에 직접 넣어주지 않아도 스프링이 알아서 넣어줌
    public UserController(UserServiceV2 userService) {
        //this.jdbcTemplate = jdbcTemplate;
        this.userService = userService; //자기가 필요한 userService가 스프링 빈이라 바로 가져오기 가능
    }

    @PostMapping("/user") // Post /user
    public void saveUser(@RequestBody UserCreateRequest request) {
        userService.saveUser(request);
    }

    //전체 유저를 조회하기 때문에 쿼리가 없음

    //fruit 반환 시 만들었던 것처럼 데이터를 담아줄 dto가 필요하다.
    //인덱스번째에 있는 이름을 가져오면 되는데, 그러면 유저에도 getter가 2개 필요
    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request){
       userService.updateUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name){
       userService.deleteUser(name);
    }


}

    //mapRow역할 : jdbc 템플릿의 쿼리가 들어온 sql을 수행. 유저 정보를 선언한 타입인 userResponse로 바꿔줌
