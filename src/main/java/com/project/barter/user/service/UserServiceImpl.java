package com.project.barter.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.barter.user.User;
import com.project.barter.user.UserRepository;
import com.project.barter.user.dto.UserLogin;
import com.project.barter.user.dto.UserPost;
import com.project.barter.user.exception.UserNotExistsException;
import com.project.barter.user.exception.LoginIdAlreadyExistsException;
import com.project.barter.user.exception.UserLoginUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public Long join(UserPost userPost) {
        Optional<User> userByLoginId = userRepository.findUserByLoginId(userPost.getLoginId());
        if(userByLoginId.isPresent())
            throw new LoginIdAlreadyExistsException();
        User joinUser = userRepository.save(objectMapper.convertValue(userPost, User.class));
        log.info("Join User ID : {} Login ID : {}",joinUser.getId(), joinUser.getLoginId());
        return joinUser.getId();
    }

    @Override
    public User login(UserLogin userLogin) {
        Optional<User> userByLoginIdAndPassword =
                userRepository.findUserByLoginIdAndPassword(userLogin.getLoginId(), userLogin.getPassword());
        if(userByLoginIdAndPassword.isEmpty())
            throw new UserLoginUnavailableException();
        log.info("Login User Login ID : {}",userLogin.getLoginId());
        return userByLoginIdAndPassword.get();
    }

    @Override
    public User findById(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if(userById.isEmpty())
            throw new UserNotExistsException();
        log.info("Find User {}",id);
        return userById.get();
    }

    @Override
    public User findByLoginId(String loginId) {
        Optional<User> userByLoginId = userRepository.findUserByLoginId(loginId);
        if(userByLoginId.isEmpty())
            throw new UserNotExistsException();
        log.info("Find By Login ID : {}",loginId);
        return userByLoginId.get();
    }
}
