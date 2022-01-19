package com.project.barter.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.barter.user.domain.Birthday;
import com.project.barter.user.dto.UserPost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserRepository userRepository;

    @DisplayName("유저 회원가입 성공")
    @Test
    public void User_Join_Success() throws Exception{
        UserPost userPost = UserPost.builder()
                .userId("userId")
                .password("password")
                .name("이름")
                .birthday(new Birthday(2020,12,12))
                .email("google@gmail.com")
                .phoneNumber("01012345678")
                .build();

        User user = objectMapper.convertValue(userPost,User.class);
        user.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userPost)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("User 회원가입",
                        requestFields(
                                fieldWithPath("userId").description("유저 로그인 아이디"),
                                fieldWithPath("password").description("유저 로그인 비밀번호"),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("birthday.year").description("유저 출생년"),
                                fieldWithPath("birthday.month").description("유저 출생월"),
                                fieldWithPath("birthday.day").description("유저 출생일"),
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("phoneNumber").description("유저 전화번호")
                        ),
                        responseFields(
                                fieldWithPath("id").description("유저 식별자"),
                                fieldWithPath("userId").description("유저 로그인 아이디"),
                                fieldWithPath("password").description("유저 로그인 비밀번호"),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("birthday.year").description("유저 출생년"),
                                fieldWithPath("birthday.month").description("유저 출생월"),
                                fieldWithPath("birthday.day").description("유저 출생일"),
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("phoneNumber").description("유저 전화번호")
                        )
                ));
    }

    @Test
    public void alreadyExistsUserIdJoin() throws Exception{
        UserPost userPost = UserPost.builder()
                .userId("userId")
                .password("password")
                .name("이름")
                .birthday(new Birthday(2020,12,12))
                .email("google@gmail.com")
                .phoneNumber("01012345678")
                .build();

        User user = objectMapper.convertValue(userPost,User.class);
        user.setId(1L);
        when(userRepository.findUserByUserId(userPost.getUserId())).thenReturn(Optional.of(user));

        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userPost)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("User 회원가입 실패 Exists UserId",
                        requestFields(
                                fieldWithPath("userId").description("유저 로그인 아이디"),
                                fieldWithPath("password").description("유저 로그인 비밀번호"),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("birthday.year").description("유저 출생년"),
                                fieldWithPath("birthday.month").description("유저 출생월"),
                                fieldWithPath("birthday.day").description("유저 출생일"),
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("phoneNumber").description("유저 전화번호")
                        )
                ));
    }

    @Test
    public void UserPostBindingError() throws Exception {
        UserPost userPost = UserPost.builder()
                .userId(" ")
                .password(" ")
                .name(" ")
                .birthday(new Birthday(3000,12,12))
                .email("googl!gmail.com")
                .phoneNumber("0112345678")
                .build();

        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userPost)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("User 회원가입 실패 Exists UserId",
                        requestFields(
                                fieldWithPath("userId").description("유저 로그인 아이디"),
                                fieldWithPath("password").description("유저 로그인 비밀번호"),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("birthday.year").description("유저 출생년"),
                                fieldWithPath("birthday.month").description("유저 출생월"),
                                fieldWithPath("birthday.day").description("유저 출생일"),
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("phoneNumber").description("유저 전화번호")
                        )
                ));

    }

}