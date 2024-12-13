package com.example.springfinalproject.controller.eventController;

import com.example.springfinalproject.model.Entity.Event;
import com.example.springfinalproject.model.Entity.EventDetail;
 import com.example.springfinalproject.model.Entity.Shop;
import com.example.springfinalproject.model.request.EventCommentRequest;
import com.example.springfinalproject.model.request.EventRequest;
import com.example.springfinalproject.model.response.ApiResponse;
 import com.example.springfinalproject.model.response.CategoryResponse;
import com.example.springfinalproject.services.EventService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Positive;
 import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
 import java.time.LocalDateTime;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/event")
@CrossOrigin
public class EventController {
 private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    //Get All Event
    @GetMapping
    @Operation(summary = "Get All Event(Customer)")
    public ResponseEntity<ApiResponse<List<Event>>> getAllEvent(
            @RequestParam(defaultValue = "1") @Positive Integer page ,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ){
        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .message("Get All Event")
                .payload(eventService.getAllEvent(page, size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //get all event current shop
    @GetMapping("/getCurrentShop")
    @Operation(summary = "Get current shop")
    public ResponseEntity<ApiResponse<List<Event>>> getCurrentShop(
            @RequestParam(defaultValue = "1") @Positive Integer page ,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ){
        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .message("Get All Event")
                .payload(eventService.getAllEventCurrentShop(page,size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //Get all product event
    @GetMapping("/productEvent")
    @Operation(summary = "Get Product Event (Customer)")
    public ResponseEntity<ApiResponse<List<Event>>> getProductEvent(
            @RequestParam(defaultValue = "1") @Positive Integer page ,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ){
        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .message("Get All Event")
                .payload(eventService.getAllProductEvent(page,size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //EVENT NEARBY USER
    @GetMapping("/nearbyEvent")
    @Operation(summary = "Get All Event nearby location(Customer)")
    public ResponseEntity<ApiResponse<List<Event>>> getNearbyEvent(String lat, String longitude ){
        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .message("Get All Event")
                .payload(eventService.getAllNearbyEvent(lat, longitude))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //Get all service event
    @GetMapping("/serviceEvent")
    @Operation(summary = "Get Service Event (Customer)")
    public ResponseEntity<ApiResponse<List<Event>>> getServiceEvent(
            @RequestParam(defaultValue = "1") @Positive Integer page ,
            @RequestParam(defaultValue = "10") @Positive Integer size
    ){
        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .message("Get All Event")
                .payload(eventService.getAllServiceEvent(page,size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //Get Event by id
    @GetMapping("/get/{id}")
    @Operation(summary = "Get Event By Id")
    public ResponseEntity<ApiResponse<EventDetail>> getEventById(@PathVariable @Positive Integer id){
        ApiResponse<EventDetail> response = ApiResponse.<EventDetail>builder()
                .message("")
                .payload(eventService.getEventDetailById(id))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //Get Event by shop Id
    @GetMapping("/shop/{shopId}")
    @Operation(summary = "Get Event By shopId")
    public ResponseEntity<ApiResponse<List<Event>>> getEventByShopId(@PathVariable @Positive Integer shopId){
        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .message("Get Event by shop Id successfully")
                .payload(eventService.getEventByShopId(shopId))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //Get Event seller
    @GetMapping("/sellerType")
    @Operation(summary = "Get event type seller")
    public ResponseEntity<ApiResponse<List<Event>>> getEventTypeSeller(){
        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .message("Get all Event type seller successfully")
                .payload(eventService.getEventTypeSeller())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //Get Event service
    @GetMapping("/serviceType")
    @Operation(summary = "Get event type service")
    public ResponseEntity<ApiResponse<List<Event>>> getEventTypeService(){
        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .message("Get all Event type service successfully")
                .payload(eventService.getEventTypeService())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //filter event only this week
    @GetMapping("/get/this-week")
    @Operation(summary = "Get event This week")
    public ResponseEntity<ApiResponse<List<Event>>> getEventThisWeek(){
        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .message("Get Event This Week")
                .payload(eventService.getEventThisWeek())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //filter event older or end date
    @GetMapping("/get/older-event")
    @Operation(summary = "Get event Oldest")
    public ResponseEntity<ApiResponse<List<Event>>> getEventOldest(){
        ApiResponse<List<Event>> response = ApiResponse.<List<Event>>builder()
                .message("Get Event order or end date")
                .payload(eventService.getEventOldest())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }

    //create new event
    @PostMapping("/create")
    @Operation(summary = "Create Event")
    public ResponseEntity<ApiResponse<Event>> addEvent(@RequestBody EventRequest eventRequest){
        ApiResponse<Event> response = null;
        Integer createId = eventService.addNewEvent(eventRequest);
        if (createId != null){
            response = ApiResponse.<Event>builder()
                    .message("Event Create Successfully")
                    .payload(eventService.getEventById(createId))
                    .httpStatus(HttpStatus.CREATED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    // post comment on event
    @PostMapping("/comment")
    @Operation(summary = "Comment on event(Customer)")
    public ResponseEntity<ApiResponse<EventDetail>> addComment(@RequestBody EventCommentRequest eventCommentRequest){
        ApiResponse<EventDetail> response = null;
        Integer createId = eventService.addCommentEvent(eventCommentRequest);
        if (createId != null){
            response = ApiResponse.<EventDetail>builder()
                    .message("Event Create Successfully")
                    .payload(eventService.getEventDetailById(createId))
                    .httpStatus(HttpStatus.CREATED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);

    }

    //update event by id
    @PutMapping("/update/{id}")
    @Operation(summary = "Edit Event(seller)")
    public ResponseEntity<ApiResponse<Event>> updateEvent(@PathVariable Integer id, @RequestBody EventRequest eventRequest){
        Integer createId = eventService.updateEvent(id,eventRequest);
        ApiResponse<Event> response = null;
        if (createId != null){
            response = ApiResponse.<Event>builder()
                    .message("Event Updated Successfully")
                    .payload(eventService.getEventById(createId))
                    .httpStatus(HttpStatus.CREATED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    //delete event by id
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete Event")
    public ResponseEntity<ApiResponse<Event>> deleteEvent(@PathVariable Integer id){
        boolean createId = eventService.deleteEvent(id);
        ApiResponse<Event> response = null;
        if (createId){
            response = ApiResponse.<Event>builder()
                    .message("Delete Event Successfully")
                    .httpStatus(HttpStatus.CREATED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }else {
            response = ApiResponse.<Event>builder()
                    .message("Delete Event Failed")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(response);
    }

}
