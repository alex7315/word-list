package com.cgm.life.service.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.cgm.life.entity.Word;
import com.cgm.life.repository.WordRepository;
import com.cgm.life.service.WordListService;

import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;

@ApplicationScoped
public class WordListServiceImpl implements WordListService{
	
	@ConfigProperty(name = "com.cgm.life.word.page.size")
	private Integer pageSize;
	
	private WordRepository wordRepository;
	
	
	@Inject
	public void setWordRepository(WordRepository wordRepository) {
		this.wordRepository = wordRepository;
	}



	@Override
	public List<String> getWords(Integer pageNumber, Direction sortDirection) {
		//@formatter:off
		var currentPageIndex = pageNumber == null ? 0 : pageNumber;
		var currentDirection = sortDirection == null ? Direction.Ascending : sortDirection;
		return wordRepository
				.findAll(Sort.by("wordValue", currentDirection))
				.page(currentPageIndex, pageSize)
				.list()
				.stream()
				.map(Word::getWordValue)
				.toList();
		//@formatter:on
	}



	@Override
	@Transactional
	public Long saveWord(String wordValue, Boolean premium) {
		Word wordToSave = Word.builder().wordValue(wordValue).premium(premium).build();
		wordRepository.persist(wordToSave);
		
		return wordToSave.getId();
	}

}
