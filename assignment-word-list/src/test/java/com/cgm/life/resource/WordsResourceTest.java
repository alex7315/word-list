package com.cgm.life.resource;

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

	private static final String USER_REGULAR = "regular";
	private static final String PASSWORD_REGULAR = "WordList_2024";

	private static final String USER_PREMIUM = "premium";
	private static final String PASSWORD_PREMIUM = "WordList_2024";

	private static final String RESPONSE_PATH_REGULAR_PAGED_JSON = "/com/cgm/life/word-list-regular-paged-response.json";
	private static final String RESPONSE_PATH_REGULAR_PAGED_DESC_JSON = "/com/cgm/life/word-list-regular-paged-response-desc.json";
	private static final String RESPONSE_PATH_REGULAR_ASC_PATH = "/com/cgm/life/word-list-regular-response-asc.json";
	private static final String RESPONSE_PATH_REGULAR_DESC_PATH = "/com/cgm/life/word-list-regular-response-desc.json";
	private static final String RESPONSE_PATH_PREMIUM_PATH = "/com/cgm/life/word-list-premium-response-asc.json";

	@Inject
	private ObjectMapper objectMapper;

	private List<String> readResource(String path) throws Exception {
		CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, String.class);
		return objectMapper.readValue(
				new InputStreamReader(getClass().getResourceAsStream(path), Charset.forName("UTF-8")), listType);
	}

	@Test
	void shouldReturnWordsInDefaultSorting() throws Exception {
		//@formatter:off
		given().auth().preemptive().basic(USER_REGULAR, PASSWORD_REGULAR)
				.when()
				.get()
				.then()
				.statusCode(Response.Status.OK.getStatusCode())
				.body(is(stringContainsInOrder(readResource(RESPONSE_PATH_REGULAR_ASC_PATH))));
		//@formatter:on
	}

	@Test
	void shouldReturnWordsDescSorting() throws Exception {
		//@formatter:off
		given().auth().preemptive().basic(USER_REGULAR, PASSWORD_REGULAR)
				.queryParam("sortDirection", Sort.Direction.Descending)
				.when()
				.get()
				.then()
				.statusCode(Response.Status.OK.getStatusCode())
				.body(is(stringContainsInOrder(readResource(RESPONSE_PATH_REGULAR_DESC_PATH))));
		//@formatter:on
	}

	@Test
	void shouldReturnWordsAscSorting() throws Exception {
		//@formatter:off
		given().auth().preemptive().basic(USER_REGULAR, PASSWORD_REGULAR)
				.queryParam("sortDirection", Sort.Direction.Ascending)
				.when()
				.get()
				.then()
				.statusCode(Response.Status.OK.getStatusCode())
				.body(is(stringContainsInOrder(readResource(RESPONSE_PATH_REGULAR_ASC_PATH))));
		//@formatter:on
	}

	@Test
	void shouldReturnAPageOfWord() throws Exception {
		//@formatter:off
		given().auth().preemptive().basic(USER_REGULAR, PASSWORD_REGULAR)
				.queryParam("pageIndex", 0)
				.queryParam("pageSize", 2)
				.when()
				.get()
				.then()
				.statusCode(Response.Status.OK.getStatusCode())
				.body(is(stringContainsInOrder(readResource(RESPONSE_PATH_REGULAR_PAGED_JSON))));
		//@formatter:on
	}

	@Test
	void shouldReturnASortedPageOfWord() throws Exception {
		//@formatter:off
		given().auth().preemptive().basic(USER_REGULAR, PASSWORD_REGULAR)
				.queryParam("pageIndex", 0)
				.queryParam("pageSize", 2)
				.queryParam("sortDirection", Sort.Direction.Descending)
				.when()
				.get()
				.then()
				.statusCode(Response.Status.OK.getStatusCode())
				.body(is(stringContainsInOrder(readResource(RESPONSE_PATH_REGULAR_PAGED_DESC_JSON))));
		//@formatter:on
	}

	@Test
	void shouldCreateANewWord() {
		//@formatter:off
		given().auth().preemptive().basic(USER_REGULAR, PASSWORD_REGULAR)
				.contentType("application/json")
				.body("NewRegularValue")
				.when()
				.post()
				.then()
				.statusCode(Response.Status.CREATED.getStatusCode())
				.header("Location", is(notNullValue()));

		given().auth().preemptive().basic(USER_REGULAR, PASSWORD_REGULAR)
				.when()
				.get()
				.then()
				.statusCode(Response.Status.OK.getStatusCode())
				.body(is(stringContainsInOrder("NewRegularValue")));
		//@formatter:on
	}

	@Test
	void shouldRejectNotAuthorizedUser() {
		//@formatter:off
		given().auth().preemptive().basic("guest", PASSWORD_REGULAR)
						.when()
						.get()
						.then()
						.statusCode(Response.Status.FORBIDDEN.getStatusCode());
		//@formatter:on
	}

	@Test
	void shouldNotReturnWordsWhenUsingBadCredentials() {
		//@formatter:off
		given().auth().preemptive().basic("BAD-USER", PASSWORD_REGULAR)
				.when()
				.get()
				.then()
				.statusCode(Response.Status.UNAUTHORIZED.getStatusCode());

		given().auth().preemptive().basic(USER_REGULAR, "BAD_USER")
				.when()
				.get()
				.then()
				.statusCode(Response.Status.UNAUTHORIZED.getStatusCode());
		//@formatter:on
	}

	@Test
	void shouldReturnPremiumWordsByPremiumUserAuthorized() throws Exception {
		//@formatter:off
		given().auth().preemptive().basic(USER_PREMIUM, PASSWORD_PREMIUM)
				.when()
				.get()
				.then()
				.statusCode(Response.Status.OK.getStatusCode())
				.body(is(stringContainsInOrder(readResource(RESPONSE_PATH_PREMIUM_PATH))));
		//@formatter:on
	}

}