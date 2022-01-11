package com.project.barter.user;

import com.project.barter.user.dto.UserPost;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @PostMapping("/join")
    public ResponseEntity join(@Validated @RequestBody UserPost userPost){
        return ResponseEntity.ok().build();
    }


}
