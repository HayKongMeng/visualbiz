package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Comment;
import com.example.springfinalproject.model.request.EventCommentRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentRepository {

    @Select("SELECT comment_id, comment_description, comment_date, username, profile_img, ut.user_id as userId " +
            "FROM comment_tb ct INNER JOIN user_tb ut on ut.user_id = ct.user_id WHERE event_id = #{id};")
    @Results(
            id = "mapper",
            value = {
                    @Result(property = "commentId", column = "comment_id"),
                    @Result(property = "commentDescription", column = "comment_description"),
                    @Result(property = "commentDate", column = "comment_date"),
                    @Result(property = "profileImg", column = "profile_img"),
                    @Result(property = "userId", column = "userId"),
                    @Result(property = "username", column = "username"),
            }
    )
    List<Comment> getCommentsByEventId(Integer id);


    //Comment on event
    @Select("""
    INSERT INTO comment_tb(comment_description, comment_date, user_id, event_id) 
    VALUES(#{rq.commentDescription}, #{rq.commentDate}, #{rq.userId}, #{rq.eventId})
    RETURNING event_id
    """)
    Integer createComment(@Param("rq") EventCommentRequest eventCommentRequest);
}
