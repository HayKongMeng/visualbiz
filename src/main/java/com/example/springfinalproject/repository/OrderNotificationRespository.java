package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Notification;
import com.example.springfinalproject.model.Entity.OrderNotification;
import com.example.springfinalproject.model.request.OrderNotificationRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderNotificationRespository {

    @Select("SELECT * FROM order_notification_tb")
    @Results(
            id = "mapper",
            value = {
                    @Result(property = "orderNotificationId", column = "order_notification_id"),
                    @Result(property = "notificationId", column = "notification_id",
                            one = @One(select = "com.example.springfinalproject.repository.NotificationRepository.findbyid")),
                    @Result(property = "orderId", column = "order_id")
            }
    )
    List<OrderNotification> findAllOrderNotification();

    @Select("SELECT notification_id FROM order_notification_tb WHERE order_id = #{id} ")
    List<Integer> findById(Integer id);

    @Select("SELECT * FROM notification_tb WHERE notification_id = #{id}")
    @ResultMap("mapper")
    Notification getAllNotiById(Integer id);

    @Select("INSERT INTO order_notification_tb(notification_id, order_id) " +
            "VALUES (#{rq.notificationId}, #{rq.orderId}) " +
            "RETURNING order_notification_id ")
    @ResultMap("mapper")
    Integer createOrderNotification(@Param("rq") OrderNotificationRequest orderNotificationRequest);
}
