package com.test.search;

import com.test.search.domain.KeywordCount;
import com.test.search.domain.KeywordHistory;
import com.test.search.service.KeywordService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SearchApplicationTests extends HttpServlet {

    private String userId;

    private String keyword;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setup() {
        MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        userId = "admin";
        keyword = "판교";
    }

    /**
     * Security - page move check
     */
    @Test
    public void checkPage() {
        ResponseEntity<String> result = template.getForEntity("/", String.class);
        assertTrue(Objects.requireNonNull(result.getHeaders().get("Location")).get(0).contains("/login"));
        result = template.getForEntity("/login", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        result = template.getForEntity("/main", String.class);
        assertTrue(Objects.requireNonNull(result.getHeaders().get("Location")).get(0).contains("/login"));
    }

    /**
     * /api/search - check keyword & parameter
     */
    @Test
    public void aCheckSearch() {
        ResponseEntity<String> result = template.getForEntity("/api/search?keyword=", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        result = template.getForEntity("/api/search?keyword=판교", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        result = template.getForEntity("/api/search?keyword=판교&size=0", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        result = template.getForEntity("/api/search?keyword=판교&size=16", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        result = template.getForEntity("/api/search?keyword=판교&size=10", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());

        result = template.getForEntity("/api/search?keyword=판교&page=0", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        result = template.getForEntity("/api/search?keyword=판교&page=46", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        result = template.getForEntity("/api/search?keyword=판교&page=20", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    /**
     * /api/search/history
     */
//    @Test
    public void bCheckSearchHistory() {
        ResponseEntity<String> result = template.getForEntity("/api/search/history", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    /**
     * /api/search/count
     */
    @Test
    public void cCheckSearchCount() {
        ResponseEntity<String> result = template.getForEntity("/api/search/count", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void dCallSearch() {
        ResponseEntity<Map> result = (ResponseEntity<Map>) keywordService.getAjaxSearch(keyword, PageRequest.of(1, 10), userId);

        assertTrue(Objects.requireNonNull(result.getBody()).size() > 0);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
    @Test
    public void eCallSearchCount() {
        List<KeywordCount> result = (List<KeywordCount>) keywordService.getAjaxSearchCount();
        assertEquals(1, result.size());

        for(int i=0; i<9; i++){
            keyword="판교"+i;
            dCallSearch();

            result = (List<KeywordCount>) keywordService.getAjaxSearchCount();
            assertEquals(result.size(), (i + 2));
        }
        keyword="판교9";
        dCallSearch();

        result = (List<KeywordCount>) keywordService.getAjaxSearchCount();
        assertEquals(10, result.size());
    }
    @Test
    public void fCallSearchHistory() {
        Page<KeywordHistory> result = (Page<KeywordHistory>) keywordService.getAjaxSearchHistoryByUserId(PageRequest.of(1, 10), userId);
        assertEquals(10, result.getContent().size());
        dCallSearch();
        result = (Page<KeywordHistory>) keywordService.getAjaxSearchHistoryByUserId(PageRequest.of(1, 15), userId);
        assertEquals(10, result.getContent().size());
    }
}