package com.vasvass.catalogservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.security.oauth2.resourceserver.jwt.issuer-uri=https://test.auth0.com/"
})
class CatalogServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
