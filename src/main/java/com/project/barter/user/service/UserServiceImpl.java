package com.project.barter.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.barter.user.User;
import com.project.barter.user.UserRepository;
import com.project.barter.user.dto.UserLogin;
import com.project.barter.user.dto.UserPost;
import com.project.barter.user.exception.UserNotExistsException;
import com.project.barter.user.exception.UserIdAlreadyExistsException;
import com.project.barter.user.exception.UserLoginUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public Long join(UserPost userPost) {
        Optional<User> userByUserId = userRepository.findUserByUserId(userPost.getUserId());
        if(userByUserId.isPresent())
            throw new UserIdAlreadyExistsException();
        User joinUser = userRepository.save(objectMapper.convertValue(userPost, User.class));
        return joinUser.getId();
    }

    @Override
    public User login(UserLogin userLogin) {
        Optional<User> userByUserIdAndPassword =
                userRepository.findUserByUserIdAndPassword(userLogin.getUserId(), userLogin.getPassword());
        if(userByUserIdAndPassword.isEmpty())
            throw new UserLoginUnavailableException();
        return userByUserIdAndPassword.get();
    }

    @Override
    public User findById(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if(userById.isEmpty())
            throw new UserNotExistsException();
        return userById.get();
    }

    @Override
    public void truncateUserTable() {
        userRepository.truncateMyTable();
    }
}
