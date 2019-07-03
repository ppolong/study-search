package com.test.search.dao;

import com.test.search.domain.KeywordCount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KeywordCountDao extends CrudRepository<KeywordCount, Long> {
    List<KeywordCount> findTop10ByOrderByCountDesc();

    KeywordCount findByKeywordEquals(String keyword);
}
