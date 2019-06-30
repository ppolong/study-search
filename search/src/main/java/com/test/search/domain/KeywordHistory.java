package com.test.search.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_SEARCH_KEYWORD_HISTORY", indexes = {@Index(columnList = "memberNo"), @Index(columnList = "regDt")})
public class KeywordHistory {
    @Id
    private long memberNo;
    private String keyword;
    private Date regDt;

    public long getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(long memberNo) {
        this.memberNo = memberNo;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getRegDt() {
        return regDt;
    }

    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    @Override
    public String toString() {
        return "KeywordHistory{" +
                "memberNo=" + memberNo +
                ", keyword='" + keyword + '\'' +
                ", regDt=" + regDt +
                '}';
    }
}