package com.test.search.controller;

import com.test.search.dao.KeywordCountDao;
import com.test.search.domain.KeywordCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ApiController {

    @Value("${area.search.by.keyword}")
    private String keywordSearchUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KeywordCountDao keywordCountDao;

    @GetMapping(value = "/api/search")
    public Object getSearchAjax(@RequestParam(value= "keyword") String keyword) {
//        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
//                .post(new URI("https://dapi.kakao.com/v2/local/search/keyword.json?query="+keyword))
//                .header("Authorization","KakaoAK 63dfc38052c3d4f47c06b6a08f71d56f")
//                .accept(MediaType.APPLICATION_JSON);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization","KakaoAK 63dfc38052c3d4f47c06b6a08f71d56f");

        KeywordCount keywordCount = new KeywordCount();
        keywordCount.setKeyword(keyword);
        keywordCount.setCount(1);

        keywordCountDao.save(keywordCount);

        return restTemplate.exchange(keywordSearchUrl+"?query="+keyword, HttpMethod.GET, new HttpEntity(httpHeaders), String.class);
//        return restTemplate.getForObject("https://dapi.kakao.com/v2/local/search/keyword.json?query="+keyword, String.class);
    }
//    public ResponseEntity<?> getSearchAjax(
//            @Valid @RequestBody String keyword, Errors errors) {
//
//        //If error, just return a 400 bad request, along with the error message
//        if (errors.hasErrors()) {
//
//            result.setMsg(errors.getAllErrors()
//                    .stream().map(x -> x.getDefaultMessage())
//                    .collect(Collectors.joining(",")));
//
//            return ResponseEntity.badRequest().body(result);
//
//        }
//
//        List<User> users = userService.findByUserNameOrEmail(search.getUsername());
//        if (users.isEmpty()) {
//            result.setMsg("no user found!");
//        } else {
//            result.setMsg("success");
//        }
//        result.setResult(users);
//
//        return ResponseEntity.ok(result);
//    }
}
