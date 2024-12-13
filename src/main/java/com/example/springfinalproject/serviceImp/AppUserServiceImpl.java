package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.repository.AppUserRepository;
import com.example.springfinalproject.services.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email);
    }
}
