package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.request.UserRequest;
import com.example.springfinalproject.model.response.UserResponse;
import jakarta.validation.constraints.Email;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

@Mapper
public interface UserRepository {

  @Select("""
    UPDATE user_tb SET username = #{us.userName},gender = #{us.gender}, address = #{us.location}
            ,profile_img = #{us.profilePicture}, cover_image = #{us.coverImage}, date_of_birth = #{us.dateOfBirth}
    WHERE user_id = #{userId} returning *;
""")
  @Results(
          id = "userMapper",
          value = {
                  @Result(property = "userId" , column = "user_id"),
                  @Result(property = "userName" , column = "username"),
                  @Result(property = "gender" , column = "gender"),
                  @Result(property = "userName" , column = "username"),
                  @Result(property = "location" , column = "address"),
                  @Result(property = "profilePicture" , column = "profile_img"),
                  @Result(property = "coverImage" , column = "cover_image"),
                  @Result(property = "dateOfBirth" , column = "date_of_birth")
          }
  )
  UserRequest editUser(@Param("us") UserRequest userRequest, Integer userId);
  @Select("""
    SELECT * from user_tb where user_id = #{userId};
""")
  UserResponse getUserInformation( Integer userId);




}

