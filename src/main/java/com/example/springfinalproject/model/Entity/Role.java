package com.example.springfinalproject.model.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Role {
    private Integer roleId;
    private String roleName;
}
