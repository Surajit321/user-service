package com.userservice.UserService.services;

import com.userservice.UserService.dtos.UserDto;
import com.userservice.UserService.models.Session;
import com.userservice.UserService.models.SessionStatus;
import com.userservice.UserService.models.User;
import com.userservice.UserService.repositiories.SessionRepository;
import com.userservice.UserService.repositiories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public ResponseEntity<UserDto> login(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            return null;
        }

        String token = RandomStringUtils.randomAlphanumeric(30);
        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);
        session.setToken(token);
        sessionRepository.save(session);

        UserDto userDto = UserDto.from(user);


        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

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
        user.setPassword(password);
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

        return SessionStatus.ACTIVE;
    }
}
