package com.endava.twitterdemo.Repository;

import com.endava.twitterdemo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //public Set<Tweet> findByIdAndTweetsDateOfCreation(Long id, Date date);
    Optional<User> findById(Long id);

}
