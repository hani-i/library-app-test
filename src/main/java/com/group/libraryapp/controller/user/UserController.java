package com.group.libraryapp.controller.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private final JdbcTemplate jdbcTemplate; //db에 접근

    //생성자를 만듦. jdbc 템플릿을 생성자에 직접 넣어주지 않아도 스프링이 알아서 넣어줌
    public UserController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/user") // Post /user
    public void saveUser(@RequestBody UserCreateRequest request) {
        String sql = "insert into user (name, age) values (?,?)";
        jdbcTemplate.update(sql, request.getName(), request.getAge()); //위 물음표에 넣어줄 수 있음!
    }

    //전체 유저를 조회하기 때문에 쿼리가 없음

    //fruit 반환 시 만들었던 것처럼 데이터를 담아줄 dto가 필요하다.
    //인덱스번째에 있는 이름을 가져오면 되는데, 그러면 유저에도 getter가 2개 필요
    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        String sql = "select *from user";
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id"); //rs = result set. 결과. 결과에서 long 타입 변수를 가져오고, 이름은 id다.
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request){
        String readsql = "select *from user where id = ?";
        boolean isUserNoExist = jdbcTemplate.query(readsql, (rs,rowNum) -> 0, request.getId()).isEmpty();

        if (isUserNoExist){
            throw new IllegalArgumentException();
        }
        String sql = "update user set name = ? where id = ?";
        jdbcTemplate.update(sql, request.getName(), request.getId());
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name){
        String readsql = "select *from user where name = ?";
        boolean isUserNoExist = jdbcTemplate.query(readsql, (rs,rowNum) -> 0, name).isEmpty();

        if (isUserNoExist){
            throw new IllegalArgumentException();
        }

        String sql = "delete from user where name = ?";
        jdbcTemplate.update(sql, name);
    }
}

    //mapRow역할 : jdbc 템플릿의 쿼리가 들어온 sql을 수행. 유저 정보를 선언한 타입인 userResponse로 바꿔줌
