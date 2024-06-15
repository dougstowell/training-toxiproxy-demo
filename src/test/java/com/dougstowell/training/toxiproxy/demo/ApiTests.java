package com.dougstowell.training.toxiproxy.demo;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiTests {
  @LocalServerPort
  private int port;

  @BeforeEach
  public void setUp() throws Exception {
    RestAssured.port = port;
  }

  @Test
  void given_the_api_is_available_then_it_should_get_the_version() {
    when().get("/api/v1/demo/").then().statusCode(200).body(equalTo("1.0"));
  }

  @Test
  void given_the_api_is_available_then_it_should_get_the_cache() {
    when().get("/api/v1/demo/cache").then().statusCode(200)
        .body(containsString("items in the cache"));
  }

  @Test
  void given_the_api_is_available_then_it_should_get_the_file() {
    when().get("/api/v1/demo/file").then().statusCode(200)
        .body(containsString("items in the file"));
  }

  @Test
  void given_we_create_a_new_cache_entry_then_it_should_report_ok() {
    when().post("/api/v1/demo/cache").then().statusCode(200)
        .body(containsString("Cache entry created"));
  }
}
