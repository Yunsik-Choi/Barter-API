package com.project.barter.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Order(1)
    @DisplayName("유저 저장 성공")
    @Test
    public void Save_User_Success(){
        User user = UserUtils.getCompleteUser();
        userRepository.save(user);
        User findUser = userRepository.findById(1L).get();
        Assertions.assertThat(1L).isEqualTo(findUser.getId());
        Assertions.assertThat("이름").isEqualTo(findUser.getName());
    }

    @DisplayName("이미 존재하는 아이디 저장 시도시 실패")
    @Test
    public void Already_Exists_UserId(){
        User user1 = UserUtils.getCompleteUser();
        User user2 = UserUtils.getCompleteUser();

        userRepository.save(user1);
        org.junit.jupiter.api.Assertions.assertThrows(
                DataIntegrityViolationException.class,
                ()-> userRepository.saveAndFlush(user2));
    }

    @DisplayName("유저아이디와 패스워드로 유저 찾기")
    @Test
    public void Find_By_UserId_And_Password(){
        User user = UserUtils.getCompleteUser();

        userRepository.save(user);

        Assertions.assertThat(user.getUserId())
                .isEqualTo(
                        userRepository.findUserByUserIdAndPassword(user.getUserId(),user.getPassword())
                        .get().getUserId()
                );
    }


    @DisplayName("유저 식별자로 조회")
    @Test
    public void Find_By_Id(){
        User user = UserUtils.getCompleteUser();
        User save = userRepository.save(user);
        Optional<User> findUser = userRepository.findById(save.getId());

        Assertions.assertThat(findUser).isNotEmpty();
        Assertions.assertThat(findUser.get().getUserId()).isEqualTo(user.getUserId());
    }

}
