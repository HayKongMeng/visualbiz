package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Notification;
import com.example.springfinalproject.model.request.NotificationRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationRepository {


    @Select("SELECT * FROM notification_tb")
    @Results(
            id = "notif_mapper",
            value = {
                    @Result(property = "notificationId", column = "notification_id"),
                    @Result(property = "notificationType", column = "notification_type"),
                    @Result(property = "notificationMessage", column = "notification_message"),
                    @Result(property = "notificationDate", column = "notification_date"),
                    @Result(property = "isRead", column = "isread")
            }
    )
    List <Notification> findAllEvent();

    @Select("SELECT * FROM notification_tb WHERE notification_id = #{id} ")
    @ResultMap("notif_mapper")
    Notification findbyid (Integer id);

    @Select("INSERT INTO notification_tb(notification_type, notification_message, notification_date, isread) " +
            "VALUES (#{rq.notificationType}, #{rq.notificationMessage}, #{rq.notificationDate}, #{rq.isRead}) " +
            "RETURNING notification_id " )
    Integer postNewNotification(@Param("rq") NotificationRequest notificationRequest);

//    @Select("UPDATE notification_tb SET notification_type = #{rq.notificationType}, notification_message = #{rq.notificationMessage}, notification_date = #{rq.notificationDate}, isread = #{rq.isRead} " +
//            "WHERE notification_id = #{id}  RETURNING notification_id ")
//    Integer updateNotification(@Param("rq") NotificationRequest notificationRequest, Integer id);

    @Select("""
        UPDATE notification_tb SET notification_type = #{rq.notificationType}, notification_message = #{rq.notificationMessage}, notification_date = #{rq.notificationDate}, isread = #{rq.isRead}
        WHERE notification_id = #{id} RETURNING notification_id
        """)
    Integer update(@Param("rq") NotificationRequest notificationRequest,Integer id);

    @Delete("DELETE FROM notification_tb WHERE notification_id = #{id} ")
    boolean deleteNotification(Integer id);
}
