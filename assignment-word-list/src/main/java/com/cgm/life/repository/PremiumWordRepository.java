package com.cgm.life.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.cgm.life.entity.PremiumWord;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class PremiumWordRepository implements PanacheRepository<PremiumWord> {
	public List<PremiumWord> findByLanguageCode(String languageCode) {
		return find("languageCode", Sort.by("sequence", Sort.Direction.Ascending), languageCode).list();
	}
}
