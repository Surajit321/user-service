package com.userservice.UserService.services;

import com.userservice.UserService.dtos.UserDto;
import com.userservice.UserService.models.Session;
import com.userservice.UserService.models.SessionStatus;
import com.userservice.UserService.models.User;
import com.userservice.UserService.repositiories.SessionRepository;
import com.userservice.UserService.repositiories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImpl(UserRepository userRepository,
                           SessionRepository sessionRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public ResponseEntity<UserDto> login(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        List<Optional<Session>> activeSessionList = sessionRepository.findAllBySession_StatusAndUser_ID(SessionStatus.ACTIVE, user.getId());

        try {
            if (!activeSessionList.isEmpty() && activeSessionList.size() >= 2) {
                throw new RuntimeException("No more sessions allowed.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }


//========================================using jwt token============================================
        MacAlgorithm alg = Jwts.SIG.HS512; //or HS384 or HS256
        SecretKey key = alg.key().build();

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("email", user.getEmail());
        jsonMap.put("roles", List.of(user.getRoles()));

        String jws = Jwts.builder().claims(jsonMap).signWith(key, alg).compact();

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setToken(jws);
        session.setExpiringAt(LocalDate.now().plusMonths(1));
        sessionRepository.save(session);

        UserDto userDto = UserDto.from(user);


        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + jws);

        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);
        return response;
    }

    @Override
    public UserDto signup(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            return null;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);

    }

    @Override
    public ResponseEntity<Void> logout(String token, Long userId) {

        Optional<Session> session = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (session.isEmpty()) {
            return null;
        }

        Session actualSession = session.get();

        actualSession.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(actualSession);

        return ResponseEntity.ok().build();
    }

    @Override
    public SessionStatus validate(Long userId, String token) {
        Optional<Session> optionalSession = sessionRepository.findByTokenAndUser_Id(token, userId);

        if (optionalSession.isEmpty()) {
            return null;
        }

        Session session = optionalSession.get();

        if (!session.getSessionStatus().equals(SessionStatus.ACTIVE)) {
            return null;
        }

        if (session.getExpiringAt().isBefore(LocalDate.now())) {
            return null;
        }

        return SessionStatus.ACTIVE;
    }
}
