package com.endava.twitterdemo.Repository;

import com.endava.twitterdemo.Model.Tweet;
import com.endava.twitterdemo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    Optional<Tweet> findById(Long id);

    Set<Tweet> findAllByUserId(Long userId);
    Set<Tweet> findAllByDateOfCreationAndUserId(Date dateOfCreatedTweet,Long userId);
    Set<Tweet> findAllUserByDateOfCreationBetween(Date afterDate,Date beforeDate);
    void deleteAllByUserId(Long id);
}
