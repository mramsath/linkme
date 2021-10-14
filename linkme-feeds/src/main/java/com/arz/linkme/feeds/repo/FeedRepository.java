package com.arz.linkme.feeds.repo;

import com.arz.linkme.feeds.model.Feed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends MongoRepository<Feed, Long> {
}
