package com.project.barter.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.barter.user.domain.Birthday;
import com.project.barter.user.dto.UserLogin;
import com.project.barter.user.dto.UserPost;
import com.project.barter.user.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User회원가입 테스트를 먼저 진행하고
 * 회원가입 테스트에서 가입된 유저를 전체 테스트에 사용한다.
 * 다른 테스트에서 유저가 생성 됬을 가능성이 있기 때문에 ApplicationContext를 초기화 한 후 테스트를 진행한다.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserService userService;

    @Order(1)
    @DisplayName("유저 회원가입 성공")
    @Test
    public void User_Join_Success() throws Exception{
        UserPost userPost = UserUtils.getCompleteUserPost();

        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userPost)))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/1"))
                .andDo(document("User 회원가입",
                        requestFields(
                                fieldWithPath("loginId").description("유저 로그인 아이디"),
                                fieldWithPath("password").description("유저 로그인 비밀번호"),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("birthday.year").description("유저 출생년"),
                                fieldWithPath("birthday.month").description("유저 출생월"),
                                fieldWithPath("birthday.day").description("유저 출생일"),
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("phoneNumber").description("유저 전화번호")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("redirect url")
                        )
                ));
    }

    @DisplayName("이미 존재하는 아이디로 회원가입 시도")
    @Test
    public void Already_Exists_LoginId() throws Exception{
        UserPost userPost = UserUtils.getCompleteUserPost();

        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userPost)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("User 회원가입 실패 Exists LoginId",
                        requestFields(
                                fieldWithPath("loginId").description("유저 로그인 아이디"),
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

    @DisplayName("UserPost객체 제약조건 준수 실패")
    @Test
    public void UserPost_Binding_Error() throws Exception {
        UserPost userPost = UserPost.builder()
                .loginId(" ")
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
                .andDo(document("User Post Binding 실패",
                        requestFields(
                                fieldWithPath("loginId").description("유저 로그인 아이디"),
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

    @DisplayName("유저 로그인 성공")
    @Test
    public void Login_User_Success() throws Exception{
        UserLogin userLogin =
                new UserLogin(UserUtils.getCompleteUserPost().getLoginId(),UserUtils.getCompleteUserPost().getPassword());

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLogin)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("User Login",
                        requestFields(
                            fieldWithPath("loginId").description("로그인시 사용하는 유저 아이디"),
                            fieldWithPath("password").description("로그인시 사용하는 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("id").description("유저 식별자"),
                                fieldWithPath("loginId").description("유저 로그인 아이디"),
                                fieldWithPath("password").description("유저 로그인 비밀번호"),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("birthday.year").description("유저 출생년"),
                                fieldWithPath("birthday.month").description("유저 출생월"),
                                fieldWithPath("birthday.day").description("유저 출생일"),
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("phoneNumber").description("유저 전화번호"),
                                subsectionWithPath("boardList").description("게시물 리스트")
                        )
                ));
    }

    @DisplayName("유저 로그인 실패")
    @Test
    public void User_Login_Fail() throws Exception{
        UserLogin unavailableUserLogin = new UserLogin("id","pw");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unavailableUserLogin)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andDo(document("User Unavailable UserLogin",
                        requestFields(
                                fieldWithPath("loginId").description("로그인시 사용하는 유저아이디"),
                                fieldWithPath("password").description("로그인시 사용하는 유저 비밀번호")
                        )
                ));
    }

    @DisplayName("유저 식별자로 조회")
    @Test
    public void Find_By_Id() throws Exception{
        mockMvc.perform(get("/user/{id}",1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("User Find By Id",
                        pathParameters(
                                parameterWithName("id").description("유저 식별자")
                        ),
                        responseFields(
                                fieldWithPath("id").description("유저 식별자"),
                                fieldWithPath("loginId").description("유저 로그인 아이디"),
                                fieldWithPath("password").description("유저 로그인 비밀번호"),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("birthday.year").description("유저 출생년"),
                                fieldWithPath("birthday.month").description("유저 출생월"),
                                fieldWithPath("birthday.day").description("유저 출생일"),
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("phoneNumber").description("유저 전화번호"),
                                subsectionWithPath("boardList").description("게시물 리스트")
                        )
                ));
    }

    @DisplayName("유저 존재하지 않는 식별자로 조회")
    @Test
    public void Find_By_Unavailable_Id() throws Exception {
        mockMvc.perform(get("/user/{id}",999L))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("User 존재하지 않는 식별자로 조회",
                        pathParameters(
                                parameterWithName("id").description("유저 식별자")
                        )
                ));
    }
}