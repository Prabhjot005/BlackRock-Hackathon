package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/welcome")
@RestController
public class TestController {

    @GetMapping
    public String welcome(){
        return "welcome to the first project with higher level request mapping";
    }

    @GetMapping("/{name}")
    public String welcomeUser(@PathVariable String name){
        return "welcome "+name+", good to see you ! Have a great time exploring me!";
    }

}
