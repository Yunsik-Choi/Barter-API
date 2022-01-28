package com.project.barter.user;

import com.project.barter.global.GlobalConst;
import com.project.barter.global.dto.EntityBody;
import com.project.barter.user.dto.LoginResponse;
import com.project.barter.user.dto.UserInfo;
import com.project.barter.user.dto.UserLogin;
import com.project.barter.user.dto.UserPost;
import com.project.barter.user.exception.CustomBindingException;
import com.project.barter.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
        Long userId = userService.join(userPost);
        response.sendRedirect("/user/"+userId);
    }

    @PostMapping("/login")
    public ResponseEntity<EntityBody<LoginResponse>> login(@RequestBody UserLogin userLogin, BindingResult bindingResult, HttpServletRequest request){
        BindingErrorCheck(bindingResult);
        User user = userService.login(userLogin);
        setLoginSession(request, user.getLoginId());
        return ResponseEntity.ok().body(new EntityBody<>(LoginResponse.byUser(user)));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<EntityBody<UserInfo>> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(new EntityBody<>(UserInfo.byUser(user)));
    }

    private void setLoginSession(HttpServletRequest request, String loginId) {
        HttpSession session = request.getSession(true);
        session.setAttribute(GlobalConst.loginSessionAttributeName,loginId);
        session.setMaxInactiveInterval(GlobalConst.loginSessionInActiveTime);
    }

    private void BindingErrorCheck(BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new CustomBindingException(bindingResult);
    }
}
