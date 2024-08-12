package com.cgm.life.testing;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

@ApplicationScoped
public class JsonResourceReader {

	@Inject
	private ObjectMapper objectMapper;

	public List<String> readResource(String path) throws Exception {
		CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, String.class);
		try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(path), Charset.forName("UTF-8"))) {
			return objectMapper.readValue(reader, listType);
		}
	}
}
