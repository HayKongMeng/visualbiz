package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.Event;
import com.example.springfinalproject.model.Entity.EventDetail;
import com.example.springfinalproject.model.Entity.Shop;
import com.example.springfinalproject.model.request.EventCommentRequest;
import com.example.springfinalproject.model.request.EventRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface EventService {
    List<Event> getAllEvent(Integer page, Integer size);

    EventDetail getEventDetailById(Integer id);

    Integer addNewEvent(EventRequest eventRequest);

    Integer updateEvent(Integer id, EventRequest eventRequest);

    boolean deleteEvent(Integer id);

    Event getEventById(Integer id);

    List<Event> getEventThisWeek();

    List<Event> getEventOldest();

    List<Event> getAllEventCurrentShop(Integer page, Integer size);

    List<Event> getEventTypeSeller();

    List<Event> getEventTypeService();

    Integer addCommentEvent(EventCommentRequest eventCommentRequest);

    List<Event> getAllProductEvent(Integer page, Integer size);

    List<Event> getAllServiceEvent(Integer page, Integer size);

    List<Event> getAllNearbyEvent(String lat, String longitude);

    List<Event> getEventByShopId(Integer shopId);
}
