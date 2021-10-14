package com.arz.linkme.feeds.controller;

import com.arz.linkme.feeds.model.Quote;
import com.arz.linkme.feeds.repo.QuoteMongoBlockingRepository;
import com.arz.linkme.feeds.repo.QuoteMongoReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@RestController
public class QuoteReactiveController {

    private static final int DELAY_PER_ITEM_MS = 100;

    private final QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    @Autowired
    private  QuoteMongoBlockingRepository quoteMongoBlockingRepository;


    public QuoteReactiveController(final QuoteMongoReactiveRepository quoteMongoReactiveRepository) {
        this.quoteMongoReactiveRepository = quoteMongoReactiveRepository;
    }

    @GetMapping("/quotes-reactive")
    public Flux<ServerSentEvent<Collection<Quote>>>getQuoteFlux() {

        return Flux.interval(Duration.ofSeconds(1))
                .map(tick -> tick == 0 ? quoteMongoBlockingRepository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(0,50)) :
                        getUpdatedQuotes())
                .filter((t)-> { return getUpdatedQuotes().size()>0; })
                .map(quotes ->
                        {
                            return ServerSentEvent.<Collection<Quote>> builder()
                                    .event("message")
                                    .data(quotes)
                                    .build();
                        }
                );
//        return quoteMongoReactiveRepository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
    }

    @GetMapping("/quotes-reactive-paged")
    public Flux<Quote> getQuoteFlux(final @RequestParam(name = "page") int page,
                                    final @RequestParam(name = "size") int size) {
        return quoteMongoReactiveRepository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(page, size))
                .delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
    }

    private List<Quote> getUpdatedQuotes() {
        LinkedList<Quote> updatedStocks = new LinkedList<>();
        quoteMongoBlockingRepository.findAllByIdNotNullOrderByIdAsc(PageRequest.of(0,50))
                .stream()
                .filter(stock -> stock.getId().contains("000"))
                .forEach(stock -> updatedStocks.add(stock));

//        System.out.println(updatedStocks.toString());
        return updatedStocks;
    }

}
