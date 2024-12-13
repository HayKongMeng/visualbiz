package com.example.springfinalproject.controller.bookmarkController;

import com.example.springfinalproject.model.Entity.Bookmark;
import com.example.springfinalproject.model.request.*;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.services.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/bookmark")
@AllArgsConstructor
@CrossOrigin
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping("/allBookmarks")
    public ResponseEntity<ApiResponse<List<AllBookmarkRequest>>> getAllBookmarks(){
        List<AllBookmarkRequest> bookmarks = bookmarkService.getAllBookmarks();
        ApiResponse<List<AllBookmarkRequest>> response = ApiResponse.<List<AllBookmarkRequest>>builder()
                .message("Get all bookmarks is successful.")
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .payload(bookmarks)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/allProductBookmarks")
    public ResponseEntity<ApiResponse<List<AllBookmarkRequest>>> getAllBookmarkProducts(){
        List<AllBookmarkRequest> bookmarks = bookmarkService.getAllBookmarkProducts();
        ApiResponse<List<AllBookmarkRequest>> response = ApiResponse.<List<AllBookmarkRequest>>builder()
                .message("Get All Bookmark Products")
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .payload(bookmarks)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/allServiceBookmarks")
    public ResponseEntity<ApiResponse<List<AllBookmarkRequest>>> getAllBookmarkServices(){
        List<AllBookmarkRequest> allServiceBookmark = bookmarkService.getAllBookmarkServices();
        ApiResponse<List<AllBookmarkRequest>> response = ApiResponse.<List<AllBookmarkRequest>>builder()
                .message("Get All Bookmark Services")
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .payload(allServiceBookmark)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/allEventBookmarks")
    public ResponseEntity<ApiResponse<List<AllBookmarkRequest>>> getAllBookmarkEvents(){
        List<AllBookmarkRequest> allEventBookmark = bookmarkService.getAllBookmarkEvents();
        ApiResponse<List<AllBookmarkRequest>> response = ApiResponse.<List<AllBookmarkRequest>>builder()
                .message("Get All Bookmark Events")
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .payload(allEventBookmark)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Book bookmark product(customer)")
    public ResponseEntity<ApiResponse<Bookmark>> createNewBookmark(@RequestBody @Valid BookmarkRequest bookmarkRequest) {
        boolean isBookmarkedProduct = bookmarkService.isBookmarkedProduct(bookmarkRequest);
        ApiResponse<Bookmark> response;
        if (isBookmarkedProduct) {
            bookmarkService.deleteBookmark(bookmarkRequest);
            response = ApiResponse.<Bookmark>builder()
                    .message("Bookmark removed successfully")
//                    .payload()
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        } else {
            bookmarkService.createNewBookmark(bookmarkRequest);
            response = ApiResponse.<Bookmark>builder()
                    .message("Bookmark added successfully")
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/service")
    @Operation(summary = "Book bookmark service(customer)")
    public ResponseEntity<ApiResponse<Bookmark>> createBookmarkWithService(@RequestBody @Valid BookmarkServiceRequest bookmarkServiceRequest) {
        boolean isBookmarked = bookmarkService.isBookmarked(bookmarkServiceRequest);
        ApiResponse<Bookmark> response;
        if (isBookmarked) {
            bookmarkService.removeBookmark(bookmarkServiceRequest);
            response = ApiResponse.<Bookmark>builder()
                    .message("Bookmark removed successfully")
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        } else {
            bookmarkService.createNewBookmarkWithService(bookmarkServiceRequest);
            response = ApiResponse.<Bookmark>builder()
                    .message("Bookmark added successfully")
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/event")
    @Operation(summary = "Book bookmark event (customer)")
    public ResponseEntity<ApiResponse<Bookmark>> createNewBookmarkWithEvent(@RequestBody @Valid BookmarkEventRequest bookmarkEventRequest) {
        boolean isBookmarkedEvent = bookmarkService.isBookmarkedEvent(bookmarkEventRequest);
        ApiResponse<Bookmark> response;
        if (isBookmarkedEvent) {
            bookmarkService.deleteBookmarkEvent(bookmarkEventRequest);
            response = ApiResponse.<Bookmark>builder()
                    .message("Bookmark removed successfully")
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        } else {
            bookmarkService.createNewBookmarkWithEvent(bookmarkEventRequest);
            response = ApiResponse.<Bookmark>builder()
                    .message("Bookmark added successfully")
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }
}
