package com.project.barter.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.barter.user.domain.Birth;
import com.project.barter.user.dto.UserPost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("유저 회원가입 성공")
    @Test
    public void User_Join_Success() throws Exception{
        UserPost userPost = UserPost.builder()
                .userId("userId")
                .password("password")
                .name("이름")
                .birth(new Birth(2020,12,12))
                .email("google@gmail.com")
                .phoneNumber("01012345678")
                .build();


        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userPost)))
                .andDo(print())
                .andExpect(status().isOk());
    }

}