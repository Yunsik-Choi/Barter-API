package com.project.barter.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.barter.user.dto.UserPost;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @PostMapping("/join")
    public ResponseEntity join(@Validated @RequestBody UserPost userPost){
        return ResponseEntity.ok().body(userRepository.save(objectMapper.convertValue(userPost,User.class)));
    }


}
