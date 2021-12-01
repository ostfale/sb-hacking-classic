package de.ostfale.book.sbhackingclassic.integration;

import de.ostfale.book.sbhackingclassic.model.Item;
import de.ostfale.book.sbhackingclassic.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test should:
 * 1. Accept HTTP Post with payload to create new Item
 * 2. Turn payload into message
 * 3. Publish to broker
 * 4. On receiving: listen for new message
 * 5. Pull one down
 * 6. Save to H2
 */
@SpringBootTest
@Testcontainers  // JUnit 5 Annotation hooks testcase in container
@AutoConfigureMockMvc
public class RabbitTest {

    // create test container which will manage instance of RabbitMQ for test
    @Container
    private static RabbitMQContainer container = new RabbitMQContainer(DockerImageName.parse("rabbitmq")
            .withTag("3.7.25-management-alpine"));

    private WebTestClient webTestClient;

    @Autowired
    private ItemRepository repository;

    // dynamically adds properties to Environment using Supplier
    @DynamicPropertySource
    private static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", container::getContainerIpAddress);
        registry.add("spring.rabbitmq.port", container::getAmqpPort);
    }

    @BeforeEach
    void setUp(@Autowired MockMvc mockMvc) {
        this.webTestClient = MockMvcWebTestClient.bindTo(mockMvc).build();
    }

    @Test
    void verifyMessagingThroughAmqp() throws InterruptedException {
        this.webTestClient.post().uri("/items")
                .bodyValue(new Item("Alf alarm clock", "nothing important", 19.99))
                .exchange()
                .expectStatus().isCreated()
                .expectBody();
        Thread.sleep(2000L);

        this.webTestClient.post().uri("/items")
                .bodyValue(new Item("Smurf TV tray", "nothing important", 29.99))
                .exchange()
                .expectStatus().isCreated()
                .expectBody();
        Thread.sleep(2000L);

        Iterable<Item> items = this.repository.findAll();
        assertThat(items).flatExtracting(Item::getName).containsExactly("Alf alarm clock", "Smurf TV tray");
        assertThat(items).flatExtracting(Item::getDescription).containsExactly("nothing important", "nothing important");
        assertThat(items).flatExtracting(Item::getPrice).containsExactly(19.99, 29.99);
    }
}
