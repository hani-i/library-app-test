package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //스프링 빈으로 등록. 바로 jdbc 이용가능
public class UserJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //해당 객체가 있는지 확인하기 위해 id만 필요하므로, UserUpdateRequest가 아닌 id만 받음
    public boolean isUserNoExist(long id){
        String readsql = "select *from user where id = ?";
        return jdbcTemplate.query(readsql, (rs,rowNum) -> 0, id).isEmpty();
    }

    public void updateUserName(String name, long id){
        String sql = "update user set name = ? where id = ?";
        jdbcTemplate.update(sql, name, id);
    }


    public boolean isUserNoExist(String name){
        String readsql = "select *from user where name = ?";
        return jdbcTemplate.query(readsql, (rs,rowNum) -> 0, name).isEmpty();

    }

    public void deleteUser(String name){
        String sql = "delete from user where name = ?";
        jdbcTemplate.update(sql, name);
    }

    public void saveUser(String name, Integer age){
        String sql = "insert into user (name, age) values (?,?)";
        jdbcTemplate.update(sql, name, age); //위 물음표에 넣어줄 수 있음!
    }

    public List<UserResponse> getUsers(){
        String sql = "select *from user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id"); //rs = result set. 결과. 결과에서 long 타입 변수를 가져오고, 이름은 id다.
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });
    }

}
