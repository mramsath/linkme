package com.arz.linkme.feeds;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Supplier;


import com.arz.linkme.feeds.model.Quote;
import com.arz.linkme.feeds.repo.QuoteMongoReactiveRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

@Component
public class QuijoteDataLoader implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(QuijoteDataLoader.class);

    private final QuoteMongoReactiveRepository quoteMongoReactiveRepository;

    QuijoteDataLoader(final QuoteMongoReactiveRepository quoteMongoReactiveRepository) {
        this.quoteMongoReactiveRepository = quoteMongoReactiveRepository;
    }

    @Override
    public void run(final ApplicationArguments args) {
        if (quoteMongoReactiveRepository.count().block() == 0L) {
            Supplier idSupplier = getIdSequenceSupplier();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(getClass()
                            .getClassLoader()
                            .getResourceAsStream("pg2000.txt"))
            );
            Flux.fromStream(
                    bufferedReader.lines()
                            .filter(l -> !l.trim().isEmpty())
                            .map(l -> quoteMongoReactiveRepository.save(
                                    new Quote(idSupplier.get().toString(),
                                            "El Quijote", l))
                            )
            ).subscribe(m -> log.info("New quote loaded: {}", m.block()));
            log.info("Repository contains now {} entries.",
                    quoteMongoReactiveRepository.count().block());
        }
    }

    private Supplier<String> getIdSequenceSupplier() {
        return new Supplier<String>() {
            Long l = 0L;

            @Override
            public String get() {
                // adds padding zeroes
                return String.format("%05d", l++);
            }
        };
    }
}
