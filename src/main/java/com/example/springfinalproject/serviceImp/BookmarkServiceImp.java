package com.example.springfinalproject.serviceImp;

import com.example.springfinalproject.exception.BadRequestException;
import com.example.springfinalproject.exception.NotFoundException;
import com.example.springfinalproject.model.Entity.Event;
import com.example.springfinalproject.model.Entity.Product;
import com.example.springfinalproject.model.Entity.ServiceApp;
import com.example.springfinalproject.model.request.*;
import com.example.springfinalproject.repository.*;
import com.example.springfinalproject.services.BookmarkService;
import com.example.springfinalproject.utils.GetCurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookmarkServiceImp implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final ProductRepository productRepository;
    private final ServiceRepository serviceRepository;
    private final EventRepository eventRepository;
    private final ShopRepository shopRepository;

    @Override
    public List<AllBookmarkRequest> getAllBookmarks() {
        Integer userId = GetCurrentUser.currentId();
        System.out.println("Fetching bookmarks for user ID: " + userId);

        List<AllBookmarkRequest> bookmarks = bookmarkRepository.getAllBookmarks(userId);

        if (bookmarks == null || bookmarks.isEmpty()) {
            System.out.println("No bookmarks found for user ID: " + userId);
        } else {
            System.out.println("Fetched bookmarks: " + bookmarks);
        }
        return bookmarks;
    }

    @Override
    public List<AllBookmarkRequest> getAllBookmarkProducts() {
        Integer userId = GetCurrentUser.currentId();
        System.out.println("Fetching bookmarks for user ID: " +userId);
        List<AllBookmarkRequest> products = bookmarkRepository.getAllBookmarkProducts(userId);
        System.out.println("Fetched products: " +products);
        return products;
    }

    @Override
    public List<AllBookmarkRequest> getAllBookmarkServices() {
        Integer userId = GetCurrentUser.currentId();
        System.out.println("Fetching bookmarks for user ID: " +userId);
        List<AllBookmarkRequest> service = bookmarkRepository.getAllBookmarkServices(userId);
        System.out.println("Fetched services: " +service);
        return service;
    }

    @Override
    public List<AllBookmarkRequest> getAllBookmarkEvents() {
        Integer userId = GetCurrentUser.currentId();
        System.out.println("Fetching bookmarks for user ID: " +userId);
        List<AllBookmarkRequest> event = bookmarkRepository.getAllBookmarkEvents(userId);
        System.out.println("Fetched evnets: " +event);
        return event;
    }

    @Override
    public Integer createNewBookmark(BookmarkRequest bookmarkRequest) {
        Integer userId = GetCurrentUser.currentId();
        boolean isBookmarkedProduct = bookmarkRepository.isBookmarkedProduct(bookmarkRequest, userId);
        if (isBookmarkedProduct) {
            bookmarkRepository.deleteBookmarks(bookmarkRequest, userId);
            return null;
        } else {
            Integer bookmarkId = bookmarkRepository.saveBookmark(userId);
            Integer shopId = shopRepository.findShopByUserId(userId);
            Product productBookmarkId = productRepository.getProductByIdForCustomer(bookmarkRequest.getProductId());
            // if (shopId != null){
                if (productBookmarkId == null){
                    throw new NotFoundException("Not found");
                }
            // }else throw new BadRequestException("Shop id ("+userId+") does not exist");
            return bookmarkRepository.insertProductBookmark(productBookmarkId, bookmarkId);
        }
    }

    @Override
    public Integer createNewBookmarkWithService(BookmarkServiceRequest bookmarkServiceRequest) {
        Integer userId = GetCurrentUser.currentId();
        boolean isBookmarked = bookmarkRepository.isBookmarked(bookmarkServiceRequest, userId);
        if (isBookmarked) {
            bookmarkRepository.deleteBookmark(bookmarkServiceRequest, userId);
            return null;
        } else {
            Integer bookmarkId = bookmarkRepository.saveBookmark(userId);
            Integer shopId = shopRepository.findShopByUserId(userId);
            ServiceApp serviceBookmarkId = serviceRepository.getServiceByIdForCustomer(bookmarkServiceRequest.getServiceId());
            // if (shopId != null){
                if (serviceBookmarkId == null){
                    throw new NotFoundException("Not found");
                }
            // }else throw new BadRequestException("Shop id ("+userId+") does not exist");
            return bookmarkRepository.insertServiceBookmark(serviceBookmarkId, bookmarkId);
        }
    }

    @Override
    public Integer createNewBookmarkWithEvent(BookmarkEventRequest bookmarkEventRequest) {
        Integer userId = GetCurrentUser.currentId();
        Integer bookmarkId = bookmarkRepository.saveBookmark(userId);
        Integer shopId = shopRepository.findShopByUserId(userId);
        Event eventBookmarkId = eventRepository.findEventById(bookmarkEventRequest.getEventId());
        // if (shopId != null){
            if (eventBookmarkId == null){
                throw new NotFoundException("Not found");
            }
        // }else throw new BadRequestException("Shop id ("+userId+") dose not exist");
        return bookmarkRepository.insertEventBookmark(eventBookmarkId, bookmarkId);
    }

    @Override
    public boolean isBookmarked(BookmarkServiceRequest bookmarkServiceRequest) {
        Integer userId = GetCurrentUser.currentId();
        return bookmarkRepository.isBookmarked(bookmarkServiceRequest, userId);
    }

    @Override
    public boolean isBookmarkedProduct(BookmarkRequest bookmarkRequest) {
        Integer userId = GetCurrentUser.currentId();
        return bookmarkRepository.isBookmarkedProduct(bookmarkRequest, userId);
    }

    @Override
    public void deleteBookmark(BookmarkRequest bookmarkRequest) {
        Integer userId = GetCurrentUser.currentId();
        bookmarkRepository.deleteBookmarks(bookmarkRequest, userId);
    }

    @Override
    public boolean isBookmarkedEvent(BookmarkEventRequest bookmarkEventRequest) {
        Integer userId = GetCurrentUser.currentId();
        return bookmarkRepository.isBookmarkedEvent(bookmarkEventRequest, userId);
    }

    @Override
    public void deleteBookmarkEvent(BookmarkEventRequest bookmarkEventRequest) {
        Integer userId = GetCurrentUser.currentId();
        bookmarkRepository.deleteBookmarkEvent(bookmarkEventRequest, userId);

    }

    @Override
    public void removeBookmark(BookmarkServiceRequest bookmarkServiceRequest) {
        Integer userId = GetCurrentUser.currentId();
        bookmarkRepository.deleteBookmark(bookmarkServiceRequest, userId);
    }
}