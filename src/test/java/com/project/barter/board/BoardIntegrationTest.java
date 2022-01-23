package com.project.barter.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.barter.board.dto.BoardPost;
import com.project.barter.global.GlobalConst;
import com.project.barter.user.User;
import com.project.barter.user.UserRepository;
import com.project.barter.user.UserUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;

import java.util.Optional;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class BoardIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServletContext servletContext;

    @Order(1)
    @DisplayName("게시물 쓰기")
    @Test
    public void Board_Write() throws Exception {
        BoardPost boardPost = BoardPost.builder().title("제목").content("내용").build();
        User save = userRepository.save(UserUtils.getCompleteUser());
        User byId = userRepository.findById(1L).get();
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setSession(setSession(save));
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        mockMvc.perform(post("/board")
                .session(setSession(save))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardPost)))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(header().exists("Location"))
                .andDo(document("Board Write",
                        requestFields(
                                fieldWithPath("title").description("게시물 제목"),
                                fieldWithPath("content").description("게시물 내용")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("redirect url")
                        )
                ));
    }

    @DisplayName("로그인 하지 않은 사용자가 게시물 작성을 시도할 경우 401에러를 반환한다.")
    @Test
    public void Board_Write_UnAuthorized() throws Exception {
        BoardPost boardPost = BoardPost.builder().title("제목").content("내용").build();

        mockMvc.perform(post("/board")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardPost)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andDo(document("Board Write UnAuthorized",
                        requestFields(
                                fieldWithPath("title").description("게시물 제목"),
                                fieldWithPath("content").description("게시물 내용")
                        )
                ));
    }

    @Transactional
    @DisplayName("게시물 식별자로 조회")
    @Test
    public void Board_Find_By_Id() throws Exception {
        BoardPost build = BoardPost.builder().title("제목").content("내용").build();
        Board completeBoard = BoardUtils.getCompleteBoard();
        completeBoard.addUser(userRepository.findById(1L).get());
        Optional<Board> byId = boardRepository.findById(1L);
        mockMvc.perform(get("/board/{id}",1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(build)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("Board 식별자로 조회",
                    pathParameters(
                            parameterWithName("id").description("게시물 식별자")
                    ),
                    responseFields(
                            fieldWithPath("id").description("게시물 식별자"),
                            fieldWithPath("title").description("게시물 제목"),
                            fieldWithPath("content").description("게시물 내용"),
                            fieldWithPath("createDate").description("게시물 작성 시간"),
                            fieldWithPath("modifiedDate").description("게시물 수정 시간"),
                            subsectionWithPath("user").description("게시물 작성 유저")
                    )
                ));
    }

    @DisplayName("게시물 존재하지 않는 식별자로 조회시 404에러 반환")
    @Test
    public void Board_Find_By_Id_No_Exists() throws Exception {
        BoardPost build = BoardPost.builder().title("제목").content("내용").build();
        mockMvc.perform(get("/board/{id}",999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(build)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("Board 존재하지 않는 식별자로 조회",
                        pathParameters(
                                parameterWithName("id").description("게시물 식별자")
                        )
                ));
    }

    @Transactional
    @DisplayName("게시물 전체 조회")
    @Test
    public void Board_Find_All() throws Exception {
        mockMvc.perform(get("/board"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("Board 전체 조회",
                        responseFields(
                                fieldWithPath("[]").description("게시물 리스트"),
                                fieldWithPath("[].id").description("게시물 식별자"),
                                fieldWithPath("[].title").description("게시물 제목"),
                                fieldWithPath("[].content").description("게시물 내용"),
                                fieldWithPath("[].createDate").description("게시물 작성 시간"),
                                fieldWithPath("[].modifiedDate").description("게시물 수정 시간"),
                                subsectionWithPath("[].user").description("게시물 작성 유저")
                        )
                ));
    }

    private MockHttpSession setSession(User user) {
        MockHttpSession mockHttpSession = new MockHttpSession(servletContext);
        mockHttpSession.setAttribute(GlobalConst.loginSessionAttributeName,user.getLoginId());
        return mockHttpSession;
    }
}