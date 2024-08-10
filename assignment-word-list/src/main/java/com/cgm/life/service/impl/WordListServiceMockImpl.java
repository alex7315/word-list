package com.cgm.life.service.impl;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.cgm.life.entity.Word;
import com.cgm.life.service.WordListService;

import io.quarkus.panache.common.Sort;

//@ApplicationScoped
public class WordListServiceMockImpl implements WordListService {
	
	private final List<String> words = List.of("Community", "Word", "List");

	@Override
	public List<String> getWords(Integer pageNumber, Sort.Direction sortDirection) {
		//@formatter:off
		Comparator<String> wordComparator = comparing(w -> w, nullsLast(naturalOrder()));
		if(Sort.Direction.Descending.equals(sortDirection)) {
			wordComparator = comparing(w -> w, nullsLast(reverseOrder()));
		}
		
		return words
				.stream()
				.sorted(wordComparator)
				.collect(toList());
		//@formatter:on
	}

	@Override
	public Long saveWord(String wordValue, Boolean premium) {
		return null;
	}

}
