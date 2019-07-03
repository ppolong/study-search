package com.test.search.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = {"/main", "/"})
    public String test() {
        return "main";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
