package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.AlreadyCreateException;

import com.example.springfinalproject.exception.BadRequestException;

import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.Book;
import com.example.springfinalproject.model.Entity.BookSeller;
import com.example.springfinalproject.model.Entity.Order;
import com.example.springfinalproject.model.Entity.Shop;
import com.example.springfinalproject.model.request.BookHistoryRequest;
import com.example.springfinalproject.model.request.BookRequest;
import com.example.springfinalproject.model.request.BookTestRequest;
import com.example.springfinalproject.model.request.ServiceBookRequest;
import com.example.springfinalproject.repository.BookRepository;

import com.example.springfinalproject.repository.CategoryRepository;
import com.example.springfinalproject.repository.ServiceRepository;

import com.example.springfinalproject.repository.ShopRepository;
import com.example.springfinalproject.services.BookService;
import com.example.springfinalproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImp implements BookService {
    private final BookRepository bookRepository;
    private final ShopRepository shopRepository;

    private final CategoryRepository categoryRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public List<BookTestRequest> AllBook() {
        Integer userId = GetCurrentUser.currentId();
        List<BookTestRequest> bookTestRequests = bookRepository.getAllBookTestRequest(userId);

        for (BookTestRequest request : bookTestRequests) {
            if (request.getPercentage() != null) {
                double discountedPrice = request.getServicePrice() * (1 - request.getPercentage() / 100.0);
                request.setServicePrice(discountedPrice);
            }
        }
        return bookTestRequests;

    }


    @Override
    public Book getBookById(Integer bookId) {
        System.out.println("bookid here = " + bookId);

        System.out.println("Test the query " + bookRepository.findBookById(bookId));
        Book book = bookRepository.findBookById(bookId);
        System.out.println(book);
        if (book == null){
            throw new NotFoundException("This book does not exist");
        }
        return book;
    }

    @Override
    public Integer createNewBook(ServiceBookRequest serviceBookRequest) {
            if (serviceBookRequest.getStartDate().size() != serviceBookRequest.getEndDate().size()) {
                throw new IllegalArgumentException("Start date and end date lists must be of the same size");
            }

            for (int i = 0; i < serviceBookRequest.getStartDate().size(); i++) {
                if (!serviceBookRequest.getStartDate().get(i).isBefore(serviceBookRequest.getEndDate().get(i))) {
                    throw new IllegalArgumentException("Start date " + serviceBookRequest.getStartDate().get(i)+ " is not before end date " + serviceBookRequest.getEndDate().get(i));
                }
            }

        LocalDateTime now = LocalDateTime.now();
        Integer userId = GetCurrentUser.currentId();

        // check shop available or not
        Shop shopId = shopRepository.findShopById(serviceBookRequest.getShop());
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }

        //check does booking is busy
        Book book = bookRepository.findBookById(userId);
        if (book != null) {
            throw new AlreadyCreateException("User already has a book.");
        }
        serviceBookRequest.setUser(userId);

        List<Integer> serviceId = serviceBookRequest.getServiceId();
        List<LocalDateTime> startDate = serviceBookRequest.getStartDate();
        List<LocalDateTime> endDate = serviceBookRequest.getEndDate();

        Integer bookId = bookRepository.addNewBook(serviceBookRequest);
        System.out.println("+++++++++++++++++++++++++"+bookId);

        // add service notification
        serviceBookRequest.setNotificationMessage("User " + bookRepository.getUsernameByUserId(userId) + " has been book " + bookId + " on " + now);
        Integer notification = bookRepository.insertNotification(now, serviceBookRequest.getNotificationMessage(), serviceBookRequest.getShop(), userId);
        bookRepository.insertOrderNotification(bookId, notification);

        //loop service
        for (int i = 0; i < serviceId.size(); i++) {
            Integer servId = serviceId.get(i);
            LocalDateTime startTime = startDate.get(i);
            LocalDateTime endTime = endDate.get(i);
            List<LocalDateTime> startDateTime = bookRepository.findServiceStartDate(servId);
            List<LocalDateTime> endDateTime = bookRepository.findServiceEndDate(servId);
            System.out.println(startDateTime);
            System.out.println(endDateTime);

            //check input start and end
            if (!startTime.isBefore(endTime)) {
                throw new IllegalArgumentException("Start Date at index " + i + " must be after End Date");
            }
            //check if busy or not

            for (int j = 0; j < startDateTime.size(); j++) {
                LocalDateTime existingStartTime = startDateTime.get(j);
                LocalDateTime existingEndTime = endDateTime.get(j);

                // If the requested start time is before an existing end time and the requested end time is after an existing start time, there is a conflict
                System.out.println(existingEndTime);
                System.out.println("ffaf"+existingStartTime);
                if (startTime.isBefore(existingEndTime) && endTime.isAfter(existingStartTime)) {
                    throw new IllegalArgumentException("The requested time conflicts with an existing booking.");
                }
            }
            bookRepository.saveBookService(bookId,servId,startTime,endTime);
        }
        System.out.println("Bookid"+bookId);
        return bookId;
    }

    @Override
    public Integer acceptBooking(Integer bookId) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        Integer foundBookId = bookRepository.findBookByShopId(bookId, shopId);
        if (foundBookId == null) {
            throw new NotFoundException("Shop does not have this appointment");
        }


        if (bookRepository.getStatusIdByBookId(bookId).equals(1)){
            return bookRepository.updateBookStatus(bookId, shopId);
        }else if (bookRepository.getStatusIdByBookId(bookId).equals(2)){
            System.out.println(bookRepository.getStatusIdByBookId(bookId));
            return bookRepository.updateBookStatusDone(bookId,shopId);
        }
        return bookId;
    }

    @Override
    public Integer cancelBook(Integer id) {
        Book book = bookRepository.findBookById(id);
        if (book == null) {
            throw new NotFoundException("Order does not exist.");
        }

        return bookRepository.cancelBook(id);
    }

    @Override
    public Integer denyBooking(Integer bookId) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        Integer foundBookId = bookRepository.findBookByShopId(bookId, shopId);
        if (foundBookId == null) {
            throw new NotFoundException("Shop does not have this appointment");
        }
        bookRepository.updateStatusDeny(bookId);
        if (bookRepository.getStatusIdByBookId(bookId).equals(2) || bookRepository.getStatusIdByBookId(bookId).equals(5)){
            throw new AlreadyCreateException("This service already accepted.");
        }
        return bookRepository.denyBooking(bookId, shopId);
    }

    @Override
    public List<BookSeller> AllBookRequest(Integer page, Integer size) {
        Integer userId = GetCurrentUser.currentId();

        // check shop available or not
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        List<BookSeller> bookSellers = bookRepository.findAllBookRequest(shopId,page,size);
        if (bookSellers.isEmpty()) {
            throw new NotFoundException("Book Request does not exist.");
        }
        return bookSellers;
    }

    @Override
    public List<BookSeller> AllBookAccept(Integer page, Integer size) {
        Integer userId = GetCurrentUser.currentId();

        // check shop available or not
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        List<BookSeller> bookSellers = bookRepository.findAllBookAccept(shopId,page,size);
        if (bookSellers.isEmpty()) {
            throw new NotFoundException("Book Accept does not exist.");
        }
        return bookSellers;
    }

    @Override
    public List<BookSeller> AllBookDone(Integer page, Integer size) {
        Integer userId = GetCurrentUser.currentId();

        // check shop available or not
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Shop does not exist.");
        }
        List<BookSeller> bookSellers = bookRepository.findAllBookDone(shopId,page,size);
        if (bookSellers.isEmpty()) {
            throw new NotFoundException("Book Accept does not exist.");
        }
        return bookSellers;
    }

    @Override
    public List<BookSeller> getAllAppointmentByServiceId(Integer id) {
        Integer userId = GetCurrentUser.currentId();
        Integer shopId = shopRepository.findShopByUserId(userId);
        if (shopId == null) {
            throw new NotFoundException("Please select a shop.");
        }

        List<BookSeller> getRequest = bookRepository.getAllAppointmentByServiceId(id,shopId);
        if (getRequest.isEmpty()){
            throw new NotFoundException("Appointment does not exist.");
        }
        return getRequest;
    }

    @Override
    public List<BookHistoryRequest> getAllBookHistoryCustomer() {
        Integer userId = GetCurrentUser.currentId();
        return bookRepository.findAllBookHistoryCustomer(userId);
    }
}
