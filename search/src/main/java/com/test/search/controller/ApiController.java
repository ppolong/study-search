package com.test.search.controller;

import com.test.search.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ApiController {

    @Autowired
    private KeywordService keywordService;

    @GetMapping(value = "/api/search")
    public Object getAjaxSearch(@RequestParam(value= "keyword", defaultValue = "") String keyword, Pageable pageable, HttpServletRequest httpServletRequest) {
        return keywordService.getAjaxSearch(keyword, pageable, httpServletRequest);
    }
    @GetMapping(value = "/api/search/history")
    public Object getAjaxSearchHistoryByUserId(Pageable pageable, HttpServletRequest httpServletRequest) {
        return keywordService.getAjaxSearchHistoryByUserId(pageable, httpServletRequest);
    }
    @GetMapping(value = "/api/search/count")
    public Object getAjaxSearchCount() {
        return keywordService.getAjaxSearchCount();
    }
}