package com.example.springfinalproject.model.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Comment {
    private Integer commentId;
    private String commentDescription;
    private String commentDate;
    private Integer userId;
    private String profileImg;
    private String username;

}
