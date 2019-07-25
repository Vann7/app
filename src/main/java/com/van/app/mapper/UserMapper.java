package com.van.app.mapper;

import com.van.app.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

   @Select("select * from user where id < 100")
   List<User> getList();

   @Select("select * from user where id = #{id}")
   User getOne(@Param("id") int id);


   @Update("update user set name = #{name}, age = #{age} where id = #{id}")
   int update(User user);

   @Select("select * from user where name = #{name}")
   User getByName(@Param("name") String name);

   @Select("select count(*) from user where age = #{age} ")
   int getCount(@Param("age") int age);

   @Insert("insert into user(name, age) values (#{name}, #{age})")
   int insertUser(User user);
}
