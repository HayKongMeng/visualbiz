package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.Status;
import com.example.springfinalproject.model.request.StatusRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatusService {

    List<Status> getAllStatus(Integer page, Integer size);

    Status getStatusById(Integer id);


}
