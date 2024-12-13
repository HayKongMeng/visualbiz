package com.example.springfinalproject.services;

import com.example.springfinalproject.model.Entity.Book;
import com.example.springfinalproject.model.Entity.BookSeller;
import com.example.springfinalproject.model.request.BookHistoryRequest;
import com.example.springfinalproject.model.request.BookRequest;
import com.example.springfinalproject.model.request.BookTestRequest;
import com.example.springfinalproject.model.request.ServiceBookRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
//    List<Book> AllBook(Integer page, Integer size);
List<BookTestRequest> AllBook();
    Book getBookById(Integer id);

    Integer createNewBook(ServiceBookRequest serviceBookRequest);

    Integer acceptBooking( Integer id);

    Integer cancelBook(Integer id);


    Integer denyBooking(Integer bookId);

    List<BookSeller> AllBookRequest(Integer page, Integer size);

    List<BookSeller> AllBookAccept(Integer page, Integer size);

    List<BookSeller> AllBookDone(Integer page, Integer size);

    List<BookSeller> getAllAppointmentByServiceId(Integer id);

    List<BookHistoryRequest> getAllBookHistoryCustomer();
}
