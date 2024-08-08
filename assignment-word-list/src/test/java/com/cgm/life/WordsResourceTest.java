package com.cgm.life;

import io.quarkus.panache.common.Sort;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;

@QuarkusTest
@TestHTTPEndpoint(WordsResource.class)
class WordsResourceTest {

    @Test
    void shouldReturnWordsInDefaultSorting() {
        given()
                .when()
                .get()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is(stringContainsInOrder("Community", "List", "Word")));
    }
    
    @Test
    void shouldReturnWordsDescSorting() {
        given()
        		.queryParam("sortDirection", Sort.Direction.Descending)
                .when()
                .get()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is(stringContainsInOrder("Word", "List", "Community")));
    }
    
    @Test
    void getWordsAscSorting() {
        given()
        		.queryParam("sortDirection", Sort.Direction.Ascending)
                .when()
                .get()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is(stringContainsInOrder("Community", "List", "Word")));
    }
}