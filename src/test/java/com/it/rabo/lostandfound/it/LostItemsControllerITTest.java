package com.it.rabo.lostandfound.it;

import com.it.rabo.lostandfound.repository.LostAndFoundRepository;
import com.it.rabo.lostandfound.util.FileUtil;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LostItemsControllerITTest {

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @LocalServerPort
    private int port;

    private RequestSpecification request;

    private Response response;

    @Autowired
    private  LostAndFoundRepository lostAndFoundRepository;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }
    @BeforeEach
    public void setUp() {

        request = with()
                .auth()
                .basic("ADMIN", "password")
                .port(port);
    }
    @Test
    public void contextLoads() {
        Assertions.assertThat(postgreSQLContainer.isRunning()).isTrue();
    }

    @Test
    public void getLostItemList() {
        String url = "http://localhost:" + port + "/lost-items";
        response = request.when().get(url);
        response.then().statusCode(200);

    }

    @Test
    public void postLostItemList() throws IOException {
        String url = "http://localhost:" + port + "/lost-items";
        File file = FileUtil.getFileFromResource("LostItems.pdf");
        response = request
                .multiPart("file", file)
                .when()
                .post(url);
        response.then().statusCode(200);
        System.out.println(lostAndFoundRepository.findAll().size());
       assert lostAndFoundRepository.findAll().size() == 4;
    }
    @Test
    public void postLostItemList_with_empty_file() throws IOException {
        String url = "http://localhost:" + port + "/lost-items";
        File file = FileUtil.getFileFromResource("LostItems_Empty.pdf");
        response = request
                .multiPart("file", file)
                .when()
                .post(url);
        response.then().statusCode(200);
        assertTrue(lostAndFoundRepository.findAll().isEmpty());
    }

    @Test
    public void postLostItemList_with_wrong_file() throws IOException {
        String url = "http://localhost:" + port + "/lost-items";
        File file = FileUtil.getFileFromResource("LostItems_corrupted.pdf");
        response = request
                .multiPart("file", file)
                .when()
                .post(url);
        response.then().statusCode(400);

    }

}
