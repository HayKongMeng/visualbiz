package com.example.springfinalproject.services;

import com.example.springfinalproject.model.request.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookmarkService {
    List<AllBookmarkRequest> getAllBookmarks();
    List<AllBookmarkRequest> getAllBookmarkProducts();
    List<AllBookmarkRequest> getAllBookmarkServices();
    List<AllBookmarkRequest> getAllBookmarkEvents();
    Integer createNewBookmark(BookmarkRequest bookmarkRequest);
    Integer createNewBookmarkWithService(BookmarkServiceRequest bookmarkServiceRequest);
    Integer createNewBookmarkWithEvent(BookmarkEventRequest bookmarkEventRequest);
    void removeBookmark(BookmarkServiceRequest bookmarkServiceRequest);
    boolean isBookmarked(BookmarkServiceRequest bookmarkServiceRequest);
    boolean isBookmarkedProduct(BookmarkRequest bookmarkRequest);
    void deleteBookmark(BookmarkRequest bookmarkRequest);
    boolean isBookmarkedEvent(BookmarkEventRequest bookmarkEventRequest);
    void deleteBookmarkEvent(BookmarkEventRequest bookmarkEventRequest);

}
