package com.project.barter.user;

import com.project.barter.user.domain.Birthday;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void saveUser(){
        User user = UserUtils.getCompleteUser();
        userRepository.save(user);
        User findUser = userRepository.findById(1L).get();
        Assertions.assertThat(1L).isEqualTo(findUser.getId());
        Assertions.assertThat("이름").isEqualTo(findUser.getName());
    }

    @Test
    public void alreadyExistsUserId(){
        User user1 = UserUtils.getCompleteUser();
        User user2 = UserUtils.getCompleteUser();

        userRepository.save(user1);
        org.junit.jupiter.api.Assertions.assertThrows(
                DataIntegrityViolationException.class,
                ()-> userRepository.saveAndFlush(user2));
    }

    @Test
    public void findByUserIdAndPassword(){
        User user = UserUtils.getCompleteUser();

        userRepository.save(user);

        Assertions.assertThat(user.getUserId())
                .isEqualTo(
                        userRepository.findUserByUserIdAndPassword(user.getUserId(),user.getPassword())
                        .get().getUserId()
                );
    }


}
