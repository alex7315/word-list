package com.cgm.life.resource;

import static com.cgm.life.testing.Constants.PASSWORD_PREMIUM;
import static com.cgm.life.testing.Constants.PASSWORD_REGULAR;
import static com.cgm.life.testing.Constants.USER_PREMIUM;
import static com.cgm.life.testing.Constants.USER_REGULAR;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.stringContainsInOrder;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Test;

import com.cgm.life.testing.ProfileProvider;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestHTTPEndpoint(WordsResource.class)
@TestProfile(ProfileProvider.PostIntegrationTest.class)
class WordResourcePostMethodIntegrationTest {

	@Test
	void shouldCreateANewWord() {
		//@formatter:off
		given().auth().preemptive().basic(USER_REGULAR, PASSWORD_REGULAR)
				.contentType("application/json")
				.body("NewRegularWord1")
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
				.body(is(stringContainsInOrder("NewRegularWord1")));
		//@formatter:on
	}

	@Test
	void shouldCreateWordOfPremiumListByPremiumUser() {
		//@formatter:off
		given().auth().preemptive().basic(USER_PREMIUM, PASSWORD_PREMIUM)
			.queryParam("premium", "true")
			.contentType("application/json")
			.body("NewPremiumWord1")
			.when()
			.post()
			.then()
			.statusCode(Response.Status.CREATED.getStatusCode())
			.header("Location", is(notNullValue()));
		
		given().auth().preemptive().basic(USER_PREMIUM, PASSWORD_PREMIUM)
			.when()
			.get("/premium")
			.then()
			.statusCode(Response.Status.OK.getStatusCode())
			.body(is(stringContainsInOrder("NewPremiumWord1")));
		
		given().auth().preemptive().basic(USER_PREMIUM, PASSWORD_PREMIUM)
			.when()
			.get()
			.then()
			.statusCode(Response.Status.OK.getStatusCode())
			.body(is(not(stringContainsInOrder("NewPremiumWord1"))));			
		//@formatter:on
	}

	@Test
	void shouldCreateWordOfRegularListByPremiumUser() {
		//@formatter:off
				given().auth().preemptive().basic(USER_REGULAR, PASSWORD_PREMIUM)
					.queryParam("premium", "false")
					.contentType("application/json")
					.body("NewRegularWord2")
					.when()
					.post()
					.then()
					.statusCode(Response.Status.CREATED.getStatusCode())
					.header("Location", is(notNullValue()));
				
				given().auth().preemptive().basic(USER_PREMIUM, PASSWORD_REGULAR)
					.when()
					.get()
					.then()
					.statusCode(Response.Status.OK.getStatusCode())
					.body(is(stringContainsInOrder("NewRegularWord2")));
				
				given().auth().preemptive().basic(USER_REGULAR, PASSWORD_REGULAR)
					.when()
					.get()
					.then()
					.statusCode(Response.Status.OK.getStatusCode())
					.body(is(stringContainsInOrder("NewRegularWord2")));			
		//@formatter:on
	}

	@Test
	void shouldRejectCreationOfPremiumWordByRegularUser() {
		//@formatter:off
			given().auth().preemptive().basic(USER_REGULAR, PASSWORD_PREMIUM)
				.queryParam("premium", "true")
				.contentType("application/json")
				.body("NewPremiumWord2")
				.when()
				.post()
				.then()
				.statusCode(Response.Status.FORBIDDEN.getStatusCode());
		//@formatter:on
	}
}
