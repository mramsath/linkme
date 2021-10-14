package com.arz.linkme.feeds.controller;

import com.arz.linkme.feeds.model.Feed;
import com.arz.linkme.feeds.repo.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@RestController
@CrossOrigin(allowedHeaders = "*")
public class FeedsController {
    private static Map<String, Feed> feedsDatabase =
            new ConcurrentHashMap<>();

    @Autowired
    FeedRepository feedRepository;

    @PutMapping("/feeds")
    public void updateStocks(@RequestBody final List<Feed> feeds) {
        feeds.forEach(feed -> {
//            feedsDatabase.put(feed.getSymbol(), feed);
            feed.setLastUpdated(System.currentTimeMillis());
            feedRepository.save(feed);
        });
    }

    @GetMapping(value = "/feeds", produces =
            MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Collection<Feed>>> liveStockChanges() {

        return Flux.interval(Duration.ofSeconds(1))
                .map(tick ->  tick == 0 ? feedRepository.findAll():
                        getUpdatedStocks())
                .filter((feeds)-> { return feeds.size()> 0; })
                .map(feeds ->
                {
//                    System.out.println("Feeds: " +feeds);
                    return ServerSentEvent.<Collection<Feed>> builder()
                        .retry(Duration.ofSeconds(1))
                        .event("message")
                        .data(feeds)
                        .build();
                });
        
    }

    private List<Feed> getUpdatedStocks() {
        LinkedList<Feed> updatedStocks = new LinkedList<>();
        feedRepository.findAll()
                .stream()
                .filter(stock -> stock.getLastUpdated() >
                        System.currentTimeMillis() - 1000)
                .forEach(stock -> updatedStocks.add(stock));
//        System.out.println(updatedStocks.toString());
        return updatedStocks;
    }
}
