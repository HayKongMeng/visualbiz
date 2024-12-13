package com.example.springfinalproject.utils;

import com.example.springfinalproject.model.Entity.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class GetCurrentUser {
    //Get current user id
    public static Integer currentId(){
        AppUser user= (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUserId();
    }
}









