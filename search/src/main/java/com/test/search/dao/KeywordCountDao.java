package com.test.search.dao;

import com.test.search.domain.KeywordCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordCountDao extends JpaRepository<KeywordCount, Integer> {
    @Override
    <S extends KeywordCount> S save(S s);
}
