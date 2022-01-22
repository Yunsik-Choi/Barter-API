package com.project.barter.user;

import com.project.barter.global.GlobalConst;
import com.project.barter.user.dto.UserLogin;
import com.project.barter.user.dto.UserPost;
import com.project.barter.user.exception.CustomBindingException;
import com.project.barter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public void join(@Validated @RequestBody UserPost userPost, BindingResult bindingResult,
                                                             HttpServletResponse response) throws IOException {
        BindingErrorCheck(bindingResult);
        Long joinUserPK = userService.join(userPost);
        response.sendRedirect("/user/"+joinUserPK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLogin userLogin, BindingResult bindingResult, HttpServletRequest request){
        BindingErrorCheck(bindingResult);
        User loginUser = userService.login(userLogin);
        HttpSession session = request.getSession(true);
        session.setAttribute(GlobalConst.loginSessionAttributeName,loginUser.getUserId());
        session.setMaxInactiveInterval(GlobalConst.loginSessionInActiveTime);
        return ResponseEntity.ok().body(loginUser);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.findById(id));
    }

    private void BindingErrorCheck(BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new CustomBindingException(bindingResult);
    }
}
