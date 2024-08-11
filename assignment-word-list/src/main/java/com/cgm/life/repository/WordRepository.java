package com.cgm.life.repository;

import javax.enterprise.context.ApplicationScoped;

import com.cgm.life.entity.Word;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class WordRepository implements PanacheRepository<Word> {

	public PanacheQuery<Word> findRegularWords(Sort sort) {
		return find("premium", sort, Boolean.FALSE);
	}
}
