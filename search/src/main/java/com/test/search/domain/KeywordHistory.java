package com.test.search.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "TB_SEARCH_KEYWORD_HISTORY", indexes = {@Index(columnList = "userId"), @Index(columnList = "regDt")})
public class KeywordHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;
    private String userId;
    private String keyword;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regDt;

    public KeywordHistory() {
    }

    public KeywordHistory(String userId, String keyword, LocalDateTime regDt) {
        this.userId = userId;
        this.keyword = keyword;
        this.regDt = regDt;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public LocalDateTime getRegDt() {
        return regDt;
    }

    public void setRegDt(LocalDateTime regDt) {
        this.regDt = regDt;
    }

    @Override
    public String toString() {
        return "KeywordHistory{" +
                "userId='" + userId + '\'' +
                ", keyword='" + keyword + '\'' +
                ", regDt=" + regDt +
                '}';
    }
}