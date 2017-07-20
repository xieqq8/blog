package com.xxx.blog.domain.mapper;

import com.xxx.blog.domain.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserMapper {

//    一个普通插入：直接用注解搞定
//    一个插入返回主键：需要使用xml来搞定
    @Insert("INSERT INTO t_user(username, address) VALUES(#{arg0},#{arg1})")
    public int insertUser(@Param("username") String username, @Param("address") String address);


    public User insertUser2(String username, String address, String sex);


    // 根据ID查找用户
    public User findUserByID(String userid);


    @Select("select * from t_user")
    @Results(value={
            @Result(id=true, column="id", property="contentId"),  // 这样可以改数据库表结构返回值
            @Result(column="username", property="username")
    })
    List<User> queryAll();

    public void deleteByPrimaryKey(Integer userid);
}