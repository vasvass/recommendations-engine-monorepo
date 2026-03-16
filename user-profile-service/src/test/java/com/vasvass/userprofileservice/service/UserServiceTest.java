package com.vasvass.userprofileservice.service;

import com.vasvass.userprofileservice.model.User;
import com.vasvass.userprofileservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void updateUser_updatesFieldsWhenUserExists() {
        User existing = new User("john", "john@example.com", "tenant-a");
        existing.setId("1");
        when(userRepository.findById("1")).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        User update = new User("johnny", "johnny@example.com", "tenant-b");

        User result = userService.updateUser("1", update);

        assertThat(result.getUsername()).isEqualTo("johnny");
        assertThat(result.getEmail()).isEqualTo("johnny@example.com");
        assertThat(result.getTenantId()).isEqualTo("tenant-b");
        verify(userRepository).save(existing);
    }

    @Test
    void updateUser_returnsNullWhenUserMissing() {
        when(userRepository.findById("missing")).thenReturn(Optional.empty());

        User update = new User("johnny", "johnny@example.com", "tenant-b");

        User result = userService.updateUser("missing", update);

        assertThat(result).isNull();
    }
}
