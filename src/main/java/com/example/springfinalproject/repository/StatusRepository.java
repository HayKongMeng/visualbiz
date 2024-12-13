package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Status;
import com.example.springfinalproject.model.request.StatusRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StatusRepository {
    @Select("SELECT * FROM status_tb " +
            "LIMIT #{size} " +
            "OFFSET ${size} * (#{page} - 1)")
    @Results(
            id = "mapper",
            value = {
                    @Result(property = "statusId", column = "status_id"),
                    @Result(property = "statusType", column = "status_type")
            }
    )
    List<Status> findAllStatus(Integer page, Integer size);

    @Select("SELECT * FROM status_tb WHERE status_id = #{id} ")
    @ResultMap("mapper")
    Status findStatusById(Integer id);


    @Select("INSERT INTO status_tb (status_type) " +
            " VALUES (#{rq.statusType}) " +
            " RETURNING status_id ")
    Integer addNewStatus(@Param("rq") StatusRequest statusRequest);

    @Select("UPDATE status_tb SET status_type = #{rq.statusType} " +
            "WHERE status_id = #{id} RETURNING status_id ")
    Integer updateStatus(@Param("rq") StatusRequest statusRequest, Integer id);

    @Delete("DELETE FROM status_tb WHERE status_id = #{id} ")
    boolean deleteStatus(Integer id);
}
