package com.cgm.life.service.impl;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.cgm.life.service.WordListService;

import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class WordListServiceMockImpl implements WordListService {
	
	private final List<String> words = List.of("Community", "Word", "List");

	@Override
	public List<String> getWords(Integer pageNumber, Sort.Direction sortDirection) {
		//@formatter:off
		Comparator<String> wordComparator = Comparator.comparing(w -> w, Comparator.nullsLast(Comparator.naturalOrder()));
		if(Sort.Direction.Descending.equals(sortDirection)) {
			wordComparator = Comparator.comparing(w -> w, Comparator.nullsLast(Comparator.reverseOrder()));
		}
		
		return words
				.stream()
				.sorted(wordComparator)
				.collect(toList());
		//@formatter:on
	}

}
