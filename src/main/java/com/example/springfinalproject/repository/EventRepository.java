package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Event;
import com.example.springfinalproject.model.Entity.EventDetail;
import com.example.springfinalproject.model.request.EventRequest;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EventRepository {

    @Select("SELECT * FROM event_tb " +
            "LIMIT #{size} OFFSET ${size} * (#{page}-1)")
    @Results(
            id = "mapper",
            value = {
                    @Result(property = "eventId", column = "event_id"),
                    @Result(property = "eventImage",column = "event_image"),
                    @Result(property = "eventDescription", column = "event_description"),
                    @Result(property = "eventAddress", column = "event_address"),
                    @Result(property = "startDate", column = "start_date"),
                    @Result(property = "endDate", column = "end_date"),
                    @Result(property = "shop", column = "shop_id",
                        one = @One(select = "com.example.springfinalproject.repository.ShopRepository.findShopById")

              
                    ),
                    @Result(property = "eventTitle", column = "event_title")
            }
    )
    List<Event> findAllEvent(Integer page, Integer size);

    //Get by Id
    @Select("SELECT * FROM event_tb WHERE event_id = #{id}")
    @Results(
            id = "eventDetail",
            value = {
                    @Result(property = "eventId", column = "event_id"),
                    @Result(property = "eventImage", column = "event_image"),
                    @Result(property = "eventDescription", column = "event_description"),
                    @Result(property = "eventAddress", column = "event_address"),
                    @Result(property = "startDate", column = "start_date"),
                    @Result(property = "endDate", column = "end_date"),
                    @Result(property = "eventTitle", column = "event_title"),
                    @Result(property = "comment", column = "event_id",
                            many = @Many(select = "com.example.springfinalproject.repository.CommentRepository.getCommentsByEventId")
                    ),
            }
    )
    EventDetail findEventDetailById(Integer id);


    @Select("SELECT * FROM event_tb WHERE event_id = #{id}")
    @ResultMap("mapper")
    Event findEventById(Integer id);


    @Select("INSERT INTO event_tb(event_title, event_description, event_image,event_address, start_date,end_date, shop_id) " +
            "VALUES (#{rq.eventTitle}, #{rq.eventDescription}, #{rq.eventImage}, #{rq.eventAddress}, #{rq.startDate}, #{rq.endDate}, #{rq.shopId}) " +
            "RETURNING event_id")
//    @ResultMap("mapper")
    Integer insertNewEvent(@Param("rq") EventRequest eventRequest);


    @Select("UPDATE event_tb SET event_title = #{rq.eventTitle}, event_description = #{rq.eventDescription}, event_image = #{rq.eventImage} , event_address = #{rq.eventAddress}, start_date = #{rq.startDate}, end_date = #{rq.endDate} " +
            "WHERE event_id = #{id} RETURNING event_id")
    Integer updateEvent(Integer id,@Param("rq") EventRequest eventRequest);

    @Delete("DELETE FROM event_tb WHERE event_id = #{id}")
    boolean deleteEvent(Integer id);



    @Select("""
    SELECT * FROM event_tb WHERE start_date BETWEEN #{startOfWeek} AND #{endOfWeek} AND is_active = true AND shop_id = #{shopId}
    """)
    @ResultMap("mapper")
    List<Event> findEventThisWeek(LocalDateTime startOfWeek, LocalDateTime endOfWeek, Integer shopId);


    @Select("UPDATE event_tb SET is_active = false WHERE end_date < #{now}")
    void updateEventEndDate(LocalDateTime now);

    @Select("SELECT * FROM event_tb WHERE is_active = false AND shop_id = #{shopId}")
    @ResultMap("mapper")
    List<Event> findEventOldest(Integer shopId);


    //Get all event current shop
    @Select("""
    SELECT * FROM event_tb WHERE shop_id = #{shopId}
    """)
    @ResultMap("mapper")
    List<Event> findAllEventCurrentShop(Integer shopId);

    //Get all event type seller
    @Select("""
    SELECT *
    FROM event_tb e
    INNER JOIN shop_tb s on e.shop_id = s.shop_id
    INNER JOIN shop_type_tb st on s.shop_type_id = st.shop_type_id
    WHERE e.shop_id= #{shopId} and (st.shop_type_id = 1 OR st.shop_type_id = 3) 
    """)
    @ResultMap("mapper")
    List<Event> findEventTypeSeller(Integer shopId);

    //Get all event type service
    @Select("""
    SELECT *
    FROM event_tb e
     INNER JOIN shop_tb s on e.shop_id = s.shop_id
     INNER JOIN shop_type_tb st on s.shop_type_id = st.shop_type_id
    WHERE  e.shop_id = #{shopId} AND (st.shop_type_id = 2 OR st.shop_type_id = 3)
    """)
    @ResultMap("mapper")
    List<Event> findEventTypeService(Integer shopId);


    //Get all product Event(Customer)
    @Select("""
    SELECT * FROM event_tb e
    INNER JOIN shop_tb s ON e.shop_id = s.shop_id
    WHERE s.shop_type_id = 1
    """)
    @ResultMap("mapper")
    List<Event> findProductEvent(Integer page, Integer size);

    //Get all service event(Customer)
    @Select("""
    SELECT * FROM event_tb e
    INNER JOIN shop_tb s ON e.shop_id = s.shop_id
    WHERE s.shop_type_id = 2
    """)
    @ResultMap("mapper")
    List<Event> findServiceEvent(Integer page, Integer size);


    //Get all event nearby User
    @Select("""
    SELECT event_tb.*, s.*,
        ST_DistanceSphere(ST_GeomFromText(#{geometry}, 4326), s.location) / 1000 AS distance
    FROM event_tb
        INNER JOIN shop_tb s ON event_tb.shop_id = s.shop_id
    WHERE ST_DistanceSphere(ST_GeomFromText(#{geometry}, 4326), s.location) / 1000 < 15
    ORDER BY distance
    """)
    @ResultMap("mapper")
    List<Event> findAllNearbyEvent(String geometry);

    //get event by shop Id
    @Select("""
    SELECT * FROM event_tb where shop_id = #{shopId}
    """)
    @ResultMap("mapper")
    List<Event> findEventByShopId(Integer shopId);
}
