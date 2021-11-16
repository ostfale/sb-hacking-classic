package de.ostfale.book.sbhackingclassic.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@DisplayName("Integration test for website loading")
public class LoadingWebsiteIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void notNull() {
        assertThat(webTestClient).isNotNull();
    }

    @Test
    void name() {
        // given
        final String result = "<form method=\"post\" action=\"/add";
        // then
        webTestClient.get().uri("/").exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_HTML)
                .expectBody(String.class)
                .consumeWith(exchangeResult ->
                        assertThat(exchangeResult.getResponseBody().contains(result)));
    }
}
