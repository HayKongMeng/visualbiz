package com.example.springfinalproject.controller.bookController;

import com.example.springfinalproject.model.Entity.Book;
import com.example.springfinalproject.model.Entity.BookSeller;
import com.example.springfinalproject.model.Entity.Event;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.request.BookHistoryRequest;
import com.example.springfinalproject.model.request.BookRequest;
import com.example.springfinalproject.model.request.BookTestRequest;
import com.example.springfinalproject.model.request.ServiceBookRequest;
import com.example.springfinalproject.model.response.ApiResponse;
import com.example.springfinalproject.model.response.BookResponse;
import com.example.springfinalproject.model.response.ProductResponse;
import com.example.springfinalproject.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/book")
@AllArgsConstructor
@CrossOrigin
public class BookController {
    private final BookService bookService;

    //Get all books
    @GetMapping
    @Operation(summary = "Get All Books in user action(customer)")
    public List<BookTestRequest> getAllBooks(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "5") Integer size){
        return bookService.AllBook();
    }

    // Get all appointment that status is waiting
    @GetMapping("/request")
    @Operation(summary = "Get all appointment Request(service)")
    public ResponseEntity<ApiResponse<List<BookSeller>>> getAllBooksRequest(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        ApiResponse<List<BookSeller>> response = ApiResponse.<List<BookSeller>>builder()
                .message("Get all appointment Request successfully")
                .payload(bookService.AllBookRequest(page, size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    // Get all appointment that status is accepted
    @GetMapping("/accept")
    @Operation(summary = "Get all appointment Accept(service)")
    public ResponseEntity<ApiResponse<List<BookSeller>>> getAllBooksAccept(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        ApiResponse<List<BookSeller>> response = ApiResponse.<List<BookSeller>>builder()
                .message("Get all appointment Accept successfully")
                .payload(bookService.AllBookAccept(page, size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    // Get all appointment that status is done
    @GetMapping("/done")
    @Operation(summary = "Get all appointment Done(service)")
    public ResponseEntity<ApiResponse<List<BookSeller>>> getAllBooksDone(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        ApiResponse<List<BookSeller>> response = ApiResponse.<List<BookSeller>>builder()
                .message("Get all appointment Done successfully")
                .payload(bookService.AllBookDone(page, size))
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //Get book by id
    @GetMapping("/{id}")
    @Operation(summary = "Get Book By Id(customer)")
    public ResponseEntity<BookResponse<Book>> getBookById(@PathVariable @Positive Integer id){
        BookResponse<Book> response = null;
        if (bookService.getBookById(id) != null) {
            response = BookResponse.<Book>builder()
                    .message("View detail of booking")
                    .payload(bookService.getBookById(id))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);

    }

    //customer
    //Get all booking history customer
    @GetMapping("/customer/history")
    @Operation(summary = "Get all history booking customer page(Customer)")
    public ResponseEntity<ApiResponse<List<BookHistoryRequest>>> getAllBookHistoryCustomerPage(){
        ApiResponse<List<BookHistoryRequest>> response = ApiResponse.<List<BookHistoryRequest>>builder()
                .message("Get all order and book history successfully")
                .payload(bookService.getAllBookHistoryCustomer())
                .httpStatus(HttpStatus.OK)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        return ResponseEntity.ok(response);
    }


    //Create New Book
    @PostMapping
    @Operation(summary = "Book any service(customer)")
    public ResponseEntity<BookResponse<Book>> addNewBook(@RequestBody @Valid ServiceBookRequest serviceBookRequest){
        BookResponse<Book> response = null;
        Integer createId = bookService.createNewBook(serviceBookRequest);
        System.out.println("created id "+createId);
        if (createId != null){
            response = BookResponse.<Book>builder()
                    .message("")
                    .payload(bookService.getBookById(createId))
                    .httpStatus(HttpStatus.CREATED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    //Accept Book
    @PutMapping("/accept/{bookId}")
    @Operation(summary = "Accept booking(seller)")
    public ResponseEntity<BookResponse<Book>> editBook(@PathVariable @Positive Integer bookId){
        Integer updateId = bookService.acceptBooking(bookId);
        BookResponse<Book> response = null;
        if (updateId!=null){
            response = BookResponse.<Book>builder()
                    .message("You have accepted booking")
                    .payload(bookService.getBookById(updateId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    //Deny book
    @PutMapping("/deny/{bookId}")
    @Operation(summary = "Deny booking(seller)")
    public ResponseEntity<BookResponse<Book>> denyBook(@PathVariable @Positive Integer bookId){
        Integer updateId = bookService.denyBooking(bookId);
        BookResponse<Book> response = null;
        if (updateId!=null){
            response = BookResponse.<Book>builder()
                    .message("You have deny the booking")
                    .payload(bookService.getBookById(updateId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    //Delete Book
    @PutMapping("/{id}")
    @Operation(summary = "Cancel Book(Customer)")
    public ResponseEntity<BookResponse<Book>> cancelBook(@PathVariable @Positive Integer id){
        Integer updateId = bookService.cancelBook(id);
        BookResponse<Book> response = null;
        if (updateId!=null){
            response = BookResponse.<Book>builder()
                    .message("You have accepted booking")
                    .payload(bookService.getBookById(updateId))
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    //get all appointment by service id
    @GetMapping("/all/request/serviceId/{id}")
    @Operation(summary = "get book all request by serviceId{id} (service provider)")
    public ResponseEntity<ApiResponse<List<BookSeller>>> getBookByServiceId(@PathVariable @Positive Integer id){
        ApiResponse<List<BookSeller>> response = null;
        List<BookSeller> book = bookService.getAllAppointmentByServiceId(id);
        if(book!=null){
            response = ApiResponse.<List<BookSeller>>builder()
                    .message("Get all book request by service id is successes.")
                    .payload(book)
                    .httpStatus(HttpStatus.OK)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
        }
        return ResponseEntity.ok(response);
    }
}
    