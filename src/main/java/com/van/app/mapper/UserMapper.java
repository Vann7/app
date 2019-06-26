package com.van.app.mapper;

import com.van.app.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

   @Select("select * from user where id < 100")
   List<User> getList();

   @Select("select * from user where id = #{id}")
   User getOne(@Param("id") int id);


   @Update("update user set name = #{name}, age = #{age} where id = #{id}")
   int update(User user);
}
