package com.cgm.life.repository;

import java.util.List;

import com.cgm.life.entity.PremiumWord;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

public class PremiumWordRepository implements PanacheRepository<PremiumWord> {
	public List<PremiumWord> findByLanguageCode(String languageCode) {
		return list("languageCode", languageCode);
	}
}
