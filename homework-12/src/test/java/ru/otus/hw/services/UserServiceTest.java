package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.models.User;
import ru.otus.hw.repositories.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = {UserRepository.class, UserServiceImpl.class})
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @DisplayName("должен проверить пользователя")
    @Test
    public void shouldLoadUserByUsername(){
        Mockito.when(userRepository.findByUserName(any(String.class)))
                .thenReturn(Optional.of(new User(1L, "user", "password")));

        assertThat(userService.loadUserByUsername("user")).isNotNull()
                .matches(user -> user.getUsername().equals("user"))
                .matches(user -> user.getPassword().equals("password"));
    }

}
