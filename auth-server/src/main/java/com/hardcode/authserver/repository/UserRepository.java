package com.hardcode.authserver.repository;


import com.hardcode.authserver.model.RegisteredUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<RegisteredUser, Long> {

    Optional<RegisteredUser> findFirstByEmailAddress(String emailAddress);
}