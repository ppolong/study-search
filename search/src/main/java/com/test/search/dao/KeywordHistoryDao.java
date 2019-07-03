package com.test.search.dao;

import com.test.search.domain.KeywordHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KeywordHistoryDao extends CrudRepository<KeywordHistory, Long> {
    List<KeywordHistory> findKeywordHistoryByUserIdEqualsOrderByRegDtDesc(String userId, Pageable pageable);

    Page<KeywordHistory> findAllByUserIdEqualsOrderByRegDtDesc(String userId, Pageable pageable);
}
