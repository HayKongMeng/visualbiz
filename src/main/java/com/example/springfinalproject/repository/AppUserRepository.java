package com.example.springfinalproject.repository;


import com.example.springfinalproject.model.Entity.AppUser;
import com.example.springfinalproject.model.request.AppUserRequest;
import com.example.springfinalproject.model.response.AppUserResponse;
import org.apache.ibatis.annotations.*;


@Mapper
public interface   AppUserRepository {
    @Results(id = "appUserMapper", value = {
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "userName", column = "username"),
                    @Result(property = "gender", column = "gender"),
                    @Result(property = "password", column = "password"),
                    @Result(property = "profileImage", column = "profile_img"),
                    @Result(property = "address", column = "address"),
                    @Result(property = "currentRole", column = "user_id",
                            many = @Many(select = "com.example.springfinalproject.repository.RoleRepository.getUserWithRole")
                    ),
                    @Result(property = "email", column = "email"),
            }
    )
    @Select("""
    SELECT * FROM user_tb WHERE email = #{email}
    """)
    AppUser findByEmail(String email);

   @ResultMap("appUserMapper")
   @Select("""
        INSERT INTO user_tb
        values( default, #{appUser.userName},'male',#{appUser.password},'phnom penh','b4fc717b-3728-4c78-8f2e-09c971781bae.jpg','customer',#{appUser.email})
        RETURNING *
    """)
   AppUser register(@Param("appUser") AppUserRequest appUserRequest);

   @ResultMap("appUserMapper")
   @Update("""
    UPDATE user_tb SET password = #{password} WHERE email = #{email}
""")
   boolean forget(String email, String password);


   @Select("""
    SELECT user_tb.user_id, user_tb.username, user_tb.gender, user_tb.email, user_tb.profile_img , user_tb.address , user_tb."current_role" FROM user_tb WHERE user_id = #{userId}
""")
    @ResultMap("appUserMapper")
   AppUserResponse findUserById(Integer userId);

    @Select("""
    select email from user_tb where user_id = #{userId}
    """)
   String findEmailByUserId(Integer userId);

    @Select("""
    SELECT us.user_id, us.username, us.gender, us.email,
           us.profile_img , us.address , us."current_role",otp.verify as isEmailVerify
    FROM user_tb us
    inner join otps_tb otp
    on us.user_id = otp.user_id
    WHERE us.user_id = #{userId};
""")
    @Result(property = "userId", column = "user_id")
    @Result(property = "profileImage", column = "profile_img")
    @Result(property = "currentRole", column = "current_role")
    @Result(property = "isEmailVerify", column = "is_email_verify")
    AppUserResponse findUserByIdAuth(Integer userId);




}



