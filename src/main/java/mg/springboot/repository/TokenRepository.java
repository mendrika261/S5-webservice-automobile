package mg.springboot.repository;

import mg.springboot.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    @Transactional
    @Modifying
    @Query("update Token t set t.lastActivity = ?1 where t.value = ?2")
    void updateLastActivityByValue(LocalDateTime lastActivity, UUID value);
    void deleteByValue(UUID value);
    Optional<Token> findByValue(UUID value);
}