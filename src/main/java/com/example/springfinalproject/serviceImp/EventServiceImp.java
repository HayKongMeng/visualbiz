package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.Event;
import com.example.springfinalproject.model.Entity.EventDetail;
import com.example.springfinalproject.model.Entity.Shop;
import com.example.springfinalproject.model.request.EventCommentRequest;
import com.example.springfinalproject.model.request.EventRequest;
import com.example.springfinalproject.repository.CommentRepository;
import com.example.springfinalproject.repository.EventRepository;
import com.example.springfinalproject.repository.ProductRepository;
import com.example.springfinalproject.repository.ShopRepository;
import com.example.springfinalproject.services.EventService;
import com.example.springfinalproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImp implements EventService {

    private final EventRepository eventRepository;
    private final ShopRepository shopRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<Event> getAllEvent(Integer page, Integer size) {
        return eventRepository.findAllEvent(page, size);
    }

    @Override
    public EventDetail getEventDetailById(Integer id) {
        Event event = eventRepository.findEventById(id);
        if (event == null) {
            throw new NotFoundException("Event id : " + id + " not exist");
        }
        return eventRepository.findEventDetailById(id);
    }

    @Override
    public Integer addNewEvent(EventRequest eventRequest) {

        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        eventRequest.setShopId(shopId);
        return eventRepository.insertNewEvent(eventRequest);
    }

    @Override
    public Integer updateEvent(Integer id, EventRequest eventRequest) {
        return eventRepository.updateEvent(id,eventRequest);
    }

    @Override
    public boolean deleteEvent(Integer id) {
        return eventRepository.deleteEvent(id);
    }

    @Override
    public Event getEventById(Integer id) {
        return eventRepository.findEventById(id);
    }

    @Override
    public List<Event> getEventThisWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        //check current shop
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }

        return eventRepository.findEventThisWeek( startOfWeek,  endOfWeek, shopId);
    }

    @Override
    public List<Event> getEventOldest() {
        //check current shop
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        return eventRepository.findEventOldest(shopId);
    }

    @Override
    public List<Event> getAllEventCurrentShop(Integer page, Integer size) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        List<Event> events = eventRepository.findAllEvent(page, size);
        if (events.isEmpty()){
            throw new NotFoundException("No events found.");
        }
        return eventRepository.findAllEventCurrentShop(shopId);
    }

    @Override
    public List<Event> getEventTypeSeller() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        List<Event> events = eventRepository.findEventTypeSeller(shopId);
        if (events.isEmpty()){
            throw new NotFoundException("No events does not exist");
        }
        return eventRepository.findEventTypeSeller(shopId);
    }

    @Override
    public List<Event> getEventTypeService() {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        System.out.println(shopId);
        List<Event> events = eventRepository.findEventTypeService(shopId);
        System.out.println(events);
        if (events.isEmpty()){
            throw new NotFoundException("No events does not exist");
        }
        return eventRepository.findEventTypeService(shopId);
    }

    @Override
    public Integer addCommentEvent(EventCommentRequest eventCommentRequest) {
        Integer userId = GetCurrentUser.currentId();
        LocalDateTime now = LocalDateTime.now();
        eventCommentRequest.setUserId(userId);
        eventCommentRequest.setCommentDate(now);
        return commentRepository.createComment(eventCommentRequest);
    }

    @Override
    public List<Event> getAllProductEvent(Integer page, Integer size) {
        List<Event> events = eventRepository.findProductEvent(page,size);
        if (events.isEmpty()){
            throw new NotFoundException("Events does not exist");
        }
        return events;
    }

    @Override
    public List<Event> getAllServiceEvent(Integer page, Integer size) {
        List<Event> events = eventRepository.findServiceEvent(page,size);
        if (events.isEmpty()){
            throw new NotFoundException("Events does not exist");
        }
        return events;
    }

    @Override
    public List<Event> getAllNearbyEvent(String lat, String longitude) {
        String point = "POINT(" + lat + " " + longitude + ")";
        // Fetch the nearby shop locations
        List<Event> events = eventRepository.findAllNearbyEvent(point);
        return events;
    }

    @Override
    public List<Event> getEventByShopId(Integer shopId) {
        Shop shop = shopRepository.findShopById(shopId);
        if (shop == null){
            throw new NotFoundException("Shop does not exist");
        }
        return eventRepository.findEventByShopId(shopId);
    }
}
