package io.github.stackpan.mgs_be_test.http;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.jayway.jsonpath.JsonPath;

import io.github.stackpan.mgs_be_test.utils.FileUtils;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.transaction.Transactional;

@SpringBootTest
@Sql(scripts = "classpath:datatests/user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@AutoConfigureMockMvc
@Transactional
public class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class Register {

        @Test
        void withValidPayloadShouldReturnsUser() throws Exception {
            var imagePath = Paths.get("src/test/resources/photo.jpg");

            var payload = """
                    {
                        "username": "ivan",
                        "password": "Ivan123!",
                        "email": "ivan@example.com",
                        "firstName": "Ivan",
                        "lastName": "Rizkyanto",
                        "profilePicture": "%s",
                        "role": "USER"
                    }
                    """.formatted(FileUtils.dataURIEncode(imagePath));

            mockMvc.perform(post("/auth/register")
                            .content(payload)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andExpect(status().isOk())
                    .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                    .andExpectAll(
                            jsonPath("$.id").isString(),
                            jsonPath("$.username").value(JsonPath.<String>read(payload, "$.username")),
                            jsonPath("$.email").value(JsonPath.<String>read(payload, "$.email")),
                            jsonPath("$.firstName").value(JsonPath.<String>read(payload, "$.firstName")),
                            jsonPath("$.lastName").value(JsonPath.<String>read(payload, "$.lastName")),
                            jsonPath("$.profilePicture").isString(),
                            jsonPath("$.role").value(JsonPath.<String>read(payload, "$.role"))
                    )
                    .andDo(result -> {
                        var responsePayload = result.getResponse().getContentAsString();

                        var profilePicture = JsonPath.<String>read(responsePayload, "$.profilePicture");

                        var path = Paths.get("uploads/" + profilePicture);

                        assertTrue(Files.exists(path));

                        Files.deleteIfExists(path);
                    });
        }

    }

    @Nested
    class Login {

        @Test
        void withCorrectCredentialsShouldReturnsToken() throws Exception {
            var payload = """
                    {
                        "username": "admin",
                        "password": "Admin123!"
                    }
                    """;

            mockMvc.perform(post("/auth/login")
                            .content(payload)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andExpect(status().isOk())
                    .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                    .andExpectAll(
                            jsonPath("$.token").isString()
                    );
        }

    }

    @Nested
    class Me {

        @Test
        void withValidTokenShouldReturnsUser() throws Exception {
            var payload = """
                    {
                        "username": "admin",
                        "password": "Admin123!"
                    }
                    """;

            var loginResponse = mockMvc.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(payload)
                    )
                    .andReturn();

            var responseBody = loginResponse.getResponse().getContentAsString();
            var token = JsonPath.<String>read(responseBody, "$.token");

            mockMvc.perform(get("/auth/me")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                    )
                    .andExpect(status().isOk())
                    .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                    .andExpectAll(
                            jsonPath("$.id").value("2abbadcc-935a-423b-b01e-ca7ac2615ce0"),
                            jsonPath("$.username").value("admin"),
                            jsonPath("$.email").value("admin@example.com"),
                            jsonPath("$.firstName").value("Admin"),
                            jsonPath("$.lastName").isEmpty(),
                            jsonPath("$.role").value("ADMIN")
                    );
        }
    }

    @Nested
    class UpdateMe {

        @Test
        void withValidPayloadShouldReturnsUpdatedUser() throws Exception {
            var payload = """
                    {
                        "username": "ivan",
                        "email": "ivan@example.com",
                        "firstName": "Ivan",
                        "lastName": "Rizkyanto"
                    }
                    """;

            var jwtMocks = jwt().jwt(jwt -> jwt
                    .claim("sub", "84d591ca-c0e3-4716-8d5f-18d1c113cb51")
                    .claim("scope", "ROLE_USER")
            );

            mockMvc.perform(put("/auth/me")
                            .with(jwtMocks)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(payload)
                    )
                    .andExpect(status().isOk())
                    .andExpectAll(
                            jsonPath("$.username").value(JsonPath.<String>read(payload, "$.username")),
                            jsonPath("$.email").value(JsonPath.<String>read(payload, "$.email")),
                            jsonPath("$.firstName").value(JsonPath.<String>read(payload, "$.firstName")),
                            jsonPath("$.lastName").value(JsonPath.<String>read(payload, "$.lastName"))
                    );
        }

        @Test
        void withInvalidPayloadShouldReturnsUpdatedUser() throws Exception {
            var payload = """
                    {
                        "username": "admin",
                        "email": "ivan@example.com",
                        "firstName": "Ivan",
                        "lastName": "Rizkyanto"
                    }
                    """;

            var jwtMocks = jwt().jwt(jwt -> jwt
                    .claim("sub", "84d591ca-c0e3-4716-8d5f-18d1c113cb51")
                    .claim("scope", "ROLE_USER")
            );

            mockMvc.perform(put("/auth/me")
                            .with(jwtMocks)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(payload)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpectAll(
                            jsonPath("$.details.username").isArray()
                    );
        }
    }

    @Nested
    class UpdatePassword {

        @Test
        void withValidCurrentPasswordShouldOk() throws Exception {
            var payload = """
                    {
                        "currentPassword": "User123!",
                        "newPassword": "User456!"
                    }
                    """;

            var jwtMocks = jwt().jwt(jwt -> jwt
                    .claim("sub", "84d591ca-c0e3-4716-8d5f-18d1c113cb51")
                    .claim("scope", "ROLE_USER")
            );

            mockMvc.perform(post("/auth/me/update-password")
                            .with(jwtMocks)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(payload)
                    )
                    .andExpect(status().isOk());
        }

        @Test
        void withWrongCurrentPasswordShouldOk() throws Exception {
            var payload = """
                    {
                        "currentPassword": "User333!",
                        "newPassword": "User456!"
                    }
                    """;

            var jwtMocks = jwt().jwt(jwt -> jwt
                    .claim("sub", "84d591ca-c0e3-4716-8d5f-18d1c113cb51")
                    .claim("scope", "ROLE_USER")
            );

            mockMvc.perform(post("/auth/me/update-password")
                            .with(jwtMocks)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(payload)
                    )
                    .andExpect(status().isBadRequest())
                    .andExpectAll(
                            jsonPath("$.details.currentPassword").isArray()
                    );
        }

    }

}
