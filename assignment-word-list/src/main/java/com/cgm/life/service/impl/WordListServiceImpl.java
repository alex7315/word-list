package com.cgm.life.service.impl;

import static java.util.stream.Stream.concat;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.cgm.life.entity.PremiumWord;
import com.cgm.life.entity.Word;
import com.cgm.life.repository.PremiumWordRepository;
import com.cgm.life.repository.WordRepository;
import com.cgm.life.service.WordListService;

import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;

@ApplicationScoped
public class WordListServiceImpl implements WordListService {

	@ConfigProperty(name = "com.cgm.life.word.page.size.default")
	private Integer pageSizeDefault;

	@ConfigProperty(name = "com.cgm.life.language.code.default")
	private String languageCodeDefault;

	private WordRepository wordRepository;

	private PremiumWordRepository premiumWordRepository;

	@Inject
	public void setWordRepository(WordRepository wordRepository) {
		this.wordRepository = wordRepository;
	}

	@Inject
	public void setPremiumWordRepository(PremiumWordRepository premiumWordRepository) {
		this.premiumWordRepository = premiumWordRepository;
	}

	@Override
	public List<String> getPremiumWords(Integer pageIndex, Integer pageSize, Direction sortDirection,
			String languageCode) {

		var currentLanguageCode = languageCode == null ? languageCodeDefault : languageCode;
		var currentPageIndex = pageIndex == null ? 0 : pageIndex;
		var currentPageSize = pageSize == null ? this.pageSizeDefault : pageSize;
		var currentDirection = sortDirection == null ? Direction.Ascending : sortDirection;

		//@formatter:off
		return concat(premiumWordRepository
						.findByLanguageCode(currentLanguageCode)
						.stream()
						.map(PremiumWord::getWordValue)
						,
						wordRepository
						.findAll(Sort.by("wordValue", currentDirection))
						.page(currentPageIndex, currentPageSize)
						.stream()
						.map(Word::getWordValue)).toList();		
		//@formatter:on
	}

	@Override
	public List<String> getRegularWords(Integer pageIndex, Integer pageSize, Direction sortDirection) {
		//@formatter:off
		var currentPageIndex = pageIndex == null ? 0 : pageIndex;
		var currentPageSize = pageSize == null ? this.pageSizeDefault : pageSize;
		var currentDirection = sortDirection == null ? Direction.Ascending : sortDirection;
		
		return wordRepository
					.findRegularWords(Sort.by("wordValue", currentDirection))
					.page(currentPageIndex, currentPageSize)
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
