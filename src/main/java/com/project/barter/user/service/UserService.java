package com.project.barter.user.service;

import com.project.barter.user.User;
import com.project.barter.user.dto.UserLogin;
import com.project.barter.user.dto.UserPost;

public interface UserService {

    Long join(UserPost userPost);

    User login(UserLogin userLogin);

    User findById(Long id);

    User findByLoginId(String loginId);

}
