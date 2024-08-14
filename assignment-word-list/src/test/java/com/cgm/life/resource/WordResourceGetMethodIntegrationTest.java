package com.cgm.life.resource;

import static com.cgm.life.testing.Constants.PASSWORD_PREMIUM;
import static com.cgm.life.testing.Constants.PASSWORD_REGULAR;
import static com.cgm.life.testing.Constants.RESPONSE_PATH_PREMIUM_JSON;
import static com.cgm.life.testing.Constants.RESPONSE_PATH_PREMIUM_PAGED_DESC_JSON;
import static com.cgm.life.testing.Constants.RESPONSE_PATH_REGULAR_ASC_JSON;
import static com.cgm.life.testing.Constants.RESPONSE_PATH_REGULAR_DESC_JSON;
import static com.cgm.life.testing.Constants.RESPONSE_PATH_REGULAR_PAGED_DESC_JSON;
import static com.cgm.life.testing.Constants.RESPONSE_PATH_REGULAR_PAGED_JSON;
import static com.cgm.life.testing.Constants.USER_PREMIUM;
import static com.cgm.life.testing.Constants.USER_REGULAR;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import com.cgm.life.testing.JsonResourceReader;
import com.cgm.life.testing.ProfileProvider;

import io.quarkus.panache.common.Sort;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestHTTPEndpoint(WordsResource.class)
@TestProfile(ProfileProvider.GetIntegrationTest.class)
class WordResourceGetMethodIntegrationTest {

	@Inject
	private JsonResourceReader jsonResourceReader;

	@Test
	void shouldReturnWordsInDefaultSorting() throws Exception {
		//@formatter:off
		given().auth().preemptive().basic(USER_REGULAR, PASSWORD_REGULAR)
				.when()
				.get()
				.then()
				.statusCode(Response.Status.OK.getStatusCode())
				.body(is(stringContainsInOrder(jsonResourceReader.readResource(RESPONSE_PATH_REGULAR_ASC_JSON))));
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
				.body(is(stringContainsInOrder(jsonResourceReader.readResource(RESPONSE_PATH_REGULAR_DESC_JSON))));
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
				.body(is(stringContainsInOrder(jsonResourceReader.readResource(RESPONSE_PATH_REGULAR_ASC_JSON))));
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
				.body(is(stringContainsInOrder(jsonResourceReader.readResource(RESPONSE_PATH_REGULAR_PAGED_JSON))));
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
				.body(is(stringContainsInOrder(jsonResourceReader.readResource(RESPONSE_PATH_REGULAR_PAGED_DESC_JSON))));
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
	void shouldReturnASortedPageOfPremiumWordList() throws Exception {
		//@formatter:off
		given().auth().preemptive().basic(USER_PREMIUM, PASSWORD_PREMIUM)
				.queryParam("pageIndex", 0)
				.queryParam("pageSize", 5)
				.queryParam("sortDirection", Sort.Direction.Descending)
				.when()
				.get("/premium")
				.then()
				.statusCode(Response.Status.OK.getStatusCode())
				.body(is(stringContainsInOrder(jsonResourceReader.readResource(RESPONSE_PATH_PREMIUM_PAGED_DESC_JSON))));
		//@formatter:on
	}

	@Test
	void shouldAllowBigWordsUserToAccessToPremiumWordList() throws Exception {
		//@formatter:off
		given().auth().preemptive().basic(USER_PREMIUM, PASSWORD_PREMIUM)
				.when()
				.get("/premium")
				.then()
				.statusCode(Response.Status.OK.getStatusCode())
				.body(is(stringContainsInOrder(jsonResourceReader.readResource(RESPONSE_PATH_PREMIUM_JSON))));
		//@formatter:on
	}

	@Test
	void shouldNotAllowEndUserToAccessToPremiumWordList() {
		//@formatter:off
		given().auth().preemptive().basic(USER_REGULAR, PASSWORD_REGULAR)
				.when()
				.get("/premium")
				.then()
				.statusCode(Response.Status.FORBIDDEN.getStatusCode());
		//@formatter:on
	}

}
