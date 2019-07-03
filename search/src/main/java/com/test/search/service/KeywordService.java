package com.test.search.service;

import com.test.search.dao.KeywordCountDao;
import com.test.search.dao.KeywordHistoryDao;
import com.test.search.domain.KeywordCount;
import com.test.search.domain.KeywordHistory;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class KeywordService {
    @Value("${area.search.by.keyword}")
    private String keywordSearchUrl;

    private final RestTemplate restTemplate;
    private final KeywordCountDao keywordCountDao;
    private final KeywordHistoryDao keywordHistoryDao;

    public KeywordService(RestTemplateBuilder restTemplateBuilder, KeywordCountDao keywordCountDao, KeywordHistoryDao keywordHistoryDao) {
        this.restTemplate = restTemplateBuilder.build();
        this.keywordCountDao = keywordCountDao;
        this.keywordHistoryDao = keywordHistoryDao;
    }

    public Object getAjaxSearch(String keyword, Pageable pageable, String userId) {
        pageable = PageRequest.of(pageable.getPageNumber(), 10);
        if(Strings.isBlank(keyword) || (pageable.getPageNumber() < 1 || pageable.getPageNumber() > 45) || (pageable.getPageSize() < 1 || pageable.getPageSize() > 15)){
            return null;
        }

        int page = (pageable.getPageNumber() <= 0) ? 1 : (pageable.getPageNumber());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK 63dfc38052c3d4f47c06b6a08f71d56f");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?query=" + keyword);
        stringBuilder.append("&page=" + page);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(keywordSearchUrl + stringBuilder.toString(), HttpMethod.GET, new HttpEntity(httpHeaders), Map.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            keywordCountDao.save(new KeywordCount(keyword, 1));
            keywordHistoryDao.save(new KeywordHistory(userId, keyword, LocalDateTime.now()));
        }

        responseEntity.getBody().put("page", page);

        return responseEntity;
    }
    public Object getAjaxSearchHistoryByUserId(Pageable pageable, String userId){
            pageable = PageRequest.of(pageable.getPageNumber(), 10);

        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10);

        return keywordHistoryDao.findAllByUserIdEqualsOrderByRegDtDesc(userId, pageable);
    }
    public Object getAjaxSearchCount(){
        return keywordCountDao.findTop10ByOrderByCountDesc();
    }
}
