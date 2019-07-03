package com.test.search.dao;

import com.test.search.domain.KeywordCount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KeywordCountDao extends CrudRepository<KeywordCount, Long> {
    List<KeywordCount> findTop10ByOrderByCountDesc();
//    @Modifying
//    @Query("update TB_SEARCH_KEYWORD_COUNT set count = (count + 1) where keyword = :keyword")
//    Integer updateKeywordCount(@Param(value="keyword") String keyword);
}
