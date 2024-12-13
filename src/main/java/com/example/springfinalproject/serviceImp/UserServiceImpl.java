package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.request.UserRequest;
import com.example.springfinalproject.model.response.UserResponse;
import com.example.springfinalproject.repository.UserRepository;
import com.example.springfinalproject.services.UserService;
import com.example.springfinalproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
 public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserRequest editUser(UserRequest userRequest) {
        Integer userId  = GetCurrentUser.currentId();

        UserRequest editUser = userRepository.editUser(userRequest,userId);
        System.out.println("user information" + editUser);
        if(editUser == null){
            throw new NotFoundException("User not found");
        }
        return editUser;
    }

    @Override
    public UserResponse getUserInformation() {
        Integer userId1 = GetCurrentUser.currentId();
        UserResponse userInformation = userRepository.getUserInformation(userId1);
        System.out.println("user information" + userInformation);
        if(userInformation == null){
            throw new NotFoundException("User not found");
        }
        return userInformation;
    }

    @Override
    public UserResponse getUserInformationWithId(Integer userId) {
        UserResponse userInformation = userRepository.getUserInformation(userId);
        if(userInformation==null){
            throw new BadRequestException(userId + " not found");
        }
        return userInformation;

    }

    @Override
    public UserResponse loginWithGoogle(UserRequest userRequest) {



        return null;
    }
}
