package com.hardcode.apigateway.repository;

import com.hardcode.apigateway.model.Token;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {
    @Transactional
    @Modifying
    @Query("delete from Token token where token.token = ?1")
    void deleteToken(String token);

    Optional<Token> findFirstByToken(String token);
}
