package com.test.search.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SEARCH_KEYWORD_COUNT")
public class KeywordCount {
    @Id
    private String keyword;
    private int count;

    public KeywordCount() {}

    public KeywordCount(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "KeywordCount{" +
                "keyword='" + keyword + '\'' +
                ", count=" + count +
                '}';
    }
}