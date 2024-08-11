package com.cgm.life.service;

import java.util.List;

import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;

public interface WordListService {

	List<String> getPremiumWords(Integer pageIndex, Integer pageSize, Sort.Direction sortDirection,
			String languageCode);

	List<String> getRegularWords(Integer pageIndex, Integer pageSize, Direction sortDirection);

	Long saveWord(String wordValue, Boolean premium);
}
