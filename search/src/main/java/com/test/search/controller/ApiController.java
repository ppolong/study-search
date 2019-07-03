package com.test.search.controller;

import com.test.search.service.KeywordService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class ApiController extends BaseController {

    private final KeywordService keywordService;

    public ApiController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping(value = "/api/search")
    public Object getAjaxSearch(@RequestParam(value = "keyword", defaultValue = "") String keyword, Pageable pageable) {
        Optional<String> userName = getUserName();
        if (!userName.isPresent()) {
            throw new IllegalStateException("not found user");
        }
        return keywordService.getAjaxSearch(keyword, pageable, userName.get());
    }

    @GetMapping(value = "/api/search/history")
    public Object getAjaxSearchHistoryByUserId(Pageable pageable, HttpServletRequest httpServletRequest) {
        Optional<String> userName = getUserName();
        if (!userName.isPresent()) {
            throw new IllegalStateException("not found user");
        }
        return keywordService.getAjaxSearchHistoryByUserId(pageable, userName.get());
    }

    @GetMapping(value = "/api/search/count")
    public Object getAjaxSearchCount() {
        return keywordService.getAjaxSearchCount();
    }
}