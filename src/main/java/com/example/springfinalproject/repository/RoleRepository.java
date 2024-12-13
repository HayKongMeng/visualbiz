package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleRepository {

    @Select("""
    SELECT DISTINCT r.role_id,r.role_name FROM user_role_tb ur
     INNER JOIN role_tb r on r.role_id = ur.role_id
     WHERE ur.user_id  = #{userId} AND NOT r.role_name ILIKE 'customer ' ;
""")
    @Result(property = "roleId", column = "role_id")
    @Result(property = "roleName", column = "role_name")
    List<Role> getUserWithRole(Integer userId);


    //Insert data to table user_role_tb
    @Select("""
    insert into user_role_tb(role_id, user_id)  values (#{roleId},#{userId});
    """)
    void insertUserRole(Integer roleId, Integer userId);

    @Select("""
    SELECT rt.role_id from role_tb rt
    INNER JOIN user_role_tb urt
    ON rt.role_id = urt.role_id
    WHERE urt.user_id = #{userId} AND rt.role_id = 3;
""")
    Integer getRoleIdForServiceProvider(Integer userId);



}
