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
        Optional<User> userByLoginId = userRepository.findUserByLoginId(userPost.getLoginId());
        if(userByLoginId.isPresent())
            throw new LoginIdAlreadyExistsException();
        User joinUser = userRepository.save(objectMapper.convertValue(userPost, User.class));
        return joinUser.getId();
    }

    @Override
    public User login(UserLogin userLogin) {
        Optional<User> userByLoginIdAndPassword =
                userRepository.findUserByLoginIdAndPassword(userLogin.getLoginId(), userLogin.getPassword());
        if(userByLoginIdAndPassword.isEmpty())
            throw new UserLoginUnavailableException();
        return userByLoginIdAndPassword.get();
    }

    @Override
    public User findById(Long id) {
        Optional<User> userById = userRepository.findById(id);
        if(userById.isEmpty())
            throw new UserNotExistsException();
        return userById.get();
    }

    @Override
    public User findByLoginId(String loginId) {
        Optional<User> userByLoginId = userRepository.findUserByLoginId(loginId);
        if(userByLoginId.isEmpty())
            throw new UserNotExistsException();
        return userByLoginId.get();
    }
}
