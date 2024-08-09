package com.cgm.life.repository;

import javax.enterprise.context.ApplicationScoped;

import com.cgm.life.entity.Word;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class WordRepository implements PanacheRepository<Word> {

}
