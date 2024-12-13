package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.model.Entity.Book;
import com.example.springfinalproject.model.Entity.Status;
import com.example.springfinalproject.model.request.StatusRequest;
import com.example.springfinalproject.repository.StatusRepository;
import com.example.springfinalproject.services.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatusServiceImp implements StatusService {
    private final StatusRepository statusRepository;


    @Override
    public List<Status> getAllStatus(Integer page, Integer size) {
        return statusRepository.findAllStatus(page, size);
    }

    @Override
    public Status getStatusById(Integer id) {
        return statusRepository.findStatusById(id);
    }

}
