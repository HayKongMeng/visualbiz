package com.example.springfinalproject.configuration;

import com.example.springfinalproject.repository.BookRepository;
import com.example.springfinalproject.repository.EventRepository;
import com.example.springfinalproject.repository.ProductRepository;
import com.example.springfinalproject.repository.ShopRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class Scheduler {

    private final EventRepository eventRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final BookRepository bookRepository;

    @Scheduled(cron = "*/5 * * * * *")
    public void scheduledTask() {
        LocalDateTime now = LocalDateTime.now();
        eventRepository.updateEventEndDate(now);
        shopRepository.updateShopNotAvailable(now);
        bookRepository.updateStatusService(now);

    }
}
