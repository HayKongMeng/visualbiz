package com.example.springfinalproject.model.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AppUser implements UserDetails {
   private Integer userId;
   private String userName;
   private String email;
   private String gender;
   private String password;
   private String profileImage;
   private String address;
   private List<Role> currentRole;
   @JsonInclude(JsonInclude.Include.NON_NULL)
   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
//      Set<Role> uniqueRoles = new HashSet<>(currentRole);
//
//      // Ensure the "customer" role is always present for new accounts
//      if (uniqueRoles.isEmpty()) {
//         uniqueRoles.add("CUSTOMER");
//      }
//
//      List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//      for (Role role : uniqueRoles) {
//         System.out.println(role);
//         authorities.add(new SimpleGrantedAuthority(role));
//      }
      return Collections.singleton(new SimpleGrantedAuthority("CUSTOMER"));
   }
   @Override
   public String getPassword() {
      return password;
   }

   @Override
   public String getUsername() {
      return email;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }


}
