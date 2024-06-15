package com.dougstowell.training.toxiproxy.demo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import io.restassured.RestAssured;
import lombok.SneakyThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiResilienceTests {
  @LocalServerPort
  private int port;

  private final ToxiProxyHelper toxiProxyHelper = new ToxiProxyHelper();

  @BeforeEach
  public void setUp() throws Exception {
    RestAssured.port = port;
  }

  @AfterEach
  public void tearDown() throws Exception {
    toxiProxyHelper.reset();
  }

  @Test
  @SneakyThrows
  void given_the_cache_is_unavailable_then_it_should_report_an_error() {
    // Arrange a connection close.
    toxiProxyHelper.getRedisProxy().toxics().timeout("sql-timeout", ToxicDirection.DOWNSTREAM,
        15000);

    // Act - call the API and assert that the error was handled.
    when().get("/api/v1/demo/cache").then().statusCode(200)
        .body(containsString("Error, could not get the cache"));
  }

  @Test
  @SneakyThrows
  void given_the_file_is_unavailable_then_it_should_report_an_error() {
    // Arrange a connection close.
    toxiProxyHelper.getLocalstackProxy().toxics().resetPeer("s3-reset-peer",
        ToxicDirection.UPSTREAM, 1);

    // Act - call the API and assert that the error was handled.
    when().post("/api/v1/demo/file").then().statusCode(200)
        .body(containsString("Error, could not create a file"));
  }
}
