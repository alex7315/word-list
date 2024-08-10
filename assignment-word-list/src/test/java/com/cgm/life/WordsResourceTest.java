package com.cgm.life;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.stringContainsInOrder;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import io.quarkus.panache.common.Sort;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestHTTPEndpoint(WordsResource.class)
class WordsResourceTest {
	
	private static final String REGULAR_RESPONSE_ASC_PATH = "/com/cgm/life/word-list-regular-response-asc.json";
	private static final String REGULAR_RESPONSE_DESC_PATH = "/com/cgm/life/word-list-regular-response-desc.json";
	
	@Inject
	private ObjectMapper objectMapper;

	
	private List<String> readResource(String path) throws Exception {
		CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, String.class);
		return objectMapper.readValue(new InputStreamReader(getClass().getResourceAsStream(path), Charset.forName("UTF-8")), listType);
	}
	
    @Test
    void shouldReturnWordsInDefaultSorting() throws Exception {
        given()
                .when()
                .get()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is(stringContainsInOrder(readResource(REGULAR_RESPONSE_ASC_PATH))));
    }
    
    @Test
    void shouldReturnWordsDescSorting() throws Exception {
        given()
        		.queryParam("sortDirection", Sort.Direction.Descending)
                .when()
                .get()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is(stringContainsInOrder(readResource(REGULAR_RESPONSE_DESC_PATH))));
    }
    
    @Test
    void shouldReturnWordsAscSorting() throws Exception {
        given()
        		.queryParam("sortDirection", Sort.Direction.Ascending)
                .when()
                .get()
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body(is(stringContainsInOrder(readResource(REGULAR_RESPONSE_ASC_PATH))));
    }
    
    @Test
    void shouldCreateANewWord() {
    	given()
    		.contentType("application/json")
    		.body("NewValue")
    		.when()
    		.post()
    		.then()
    		.statusCode(Response.Status.CREATED.getStatusCode())
    		.header("Location", is(notNullValue()));
    	
    	given()
        	.when()
        	.get()
        	.then()
        	.statusCode(Response.Status.OK.getStatusCode())
        .body(is(stringContainsInOrder("NewValue")));
    }
}