package com.cgm.life.testing;

import java.util.Set;

import io.quarkus.test.junit.QuarkusTestProfile;

public final class ProfileProvider {

	private ProfileProvider() {
	}

	public static class GetIntegrationTest implements QuarkusTestProfile {

		@Override
		public Set<String> tags() {
			return Set.of("GET-Integration");
		}

	}

	public static class PostIntegrationTest implements QuarkusTestProfile {

		@Override
		public Set<String> tags() {
			return Set.of("POST-Integration");
		}

	}

}
