package com.userservice.UserService.repositiories;

import com.userservice.UserService.models.Session;
import com.userservice.UserService.models.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByTokenAndUser_Id(String token, Long userId);

    @Query(value = "select * from Session s where s.session_status = ?1 and s.user_id = ?2", nativeQuery = true)
    List<Optional<Session>> findAllBySession_StatusAndUser_ID(SessionStatus status, Long userId);
}
