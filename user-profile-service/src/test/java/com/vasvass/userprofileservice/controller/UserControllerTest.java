package com.vasvass.userprofileservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasvass.userprofileservice.SecurityConfig;
import com.vasvass.userprofileservice.model.User;
import com.vasvass.userprofileservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
@SuppressWarnings({"null", "removal"})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsers_returnsList() throws Exception {
        User user = new User("john", "john@example.com", "tenant-a");
        user.setId("1");
        when(userService.findAll()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].username").value("john"));
    }

    @Test
    void getUserById_returnsUser() throws Exception {
        User user = new User("john", "john@example.com", "tenant-a");
        user.setId("1");
        when(userService.findById("1")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void createUser_returnsCreatedUser() throws Exception {
        User created = new User("john", "john@example.com", "tenant-a");
        created.setId("1");
        when(userService.createUser(any(User.class))).thenReturn(created);

        User payload = new User("john", "john@example.com", "tenant-a");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.tenantId").value("tenant-a"));
    }

    @Test
    void updateUser_returnsUpdatedUser() throws Exception {
        User updated = new User("johnny", "johnny@example.com", "tenant-b");
        updated.setId("1");
        when(userService.updateUser(any(String.class), any(User.class))).thenReturn(updated);

        User payload = new User("johnny", "johnny@example.com", "tenant-b");

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("johnny"))
                .andExpect(jsonPath("$.email").value("johnny@example.com"));
    }

    @Test
    void deleteUser_returnsOk() throws Exception {
        doNothing().when(userService).deleteUser("1");

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());

        verify(userService).deleteUser("1");
    }
}
