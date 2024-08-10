package com.cgm.life.service;

import java.util.List;

import io.quarkus.panache.common.Sort;

public interface WordListService {

	List<String> getWords(Integer pageNumber, Sort.Direction sortDirection);
	
	Long saveWord(String wordValue, Boolean premium);
}
