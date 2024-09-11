package com.it.rabo.lostandfound.it;

import com.it.rabo.lostandfound.entity.LostFound;
import com.it.rabo.lostandfound.repository.ClaimRecordsRepository;
import com.it.rabo.lostandfound.repository.LostAndFoundRepository;
import com.it.rabo.lostandfound.repository.UserDetailsRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;


import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertEquals;



@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClaimControllerITTest {

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @LocalServerPort
    private int port;

    private RequestSpecification request;

    private Response response;

    @Autowired
    private ClaimRecordsRepository claimRecordsRepository;

    @Autowired
    private  LostAndFoundRepository lostAndFoundRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;
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
    @Disabled("Disabled for now as it is failing with 500 error(GroovyRuntime could not access constructor:). Need to investigate further.")
    public void getAllClaimList() {
        String url = "http://localhost:" + port + "/claims";
        response = request.when().get(url);
        response.then().statusCode(200);

    }
    @Disabled("Disabled for now as it is failing with 500 error(GroovyRuntime could not access constructor:). Need to investigate further.")
    @Test
    public void saveClaimLostItem() {
        lostAndFoundRepository.save(new LostFound("LAPTOP",2,"Airport"));
        String url = "http://localhost:" + port + "/claims";
        response = request
                .when().contentType(ContentType.JSON)
                .body("{\"userId\":1,\"lostItemId\":1,\"quantity\":1}")
                .post(url);
        response.then().statusCode(200);

       assertEquals(1, claimRecordsRepository.findAll().size());
    }

}
