package com.example.ui.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
	
	@RequestMapping("/hello")
    public String readFile() {
        return "Hello Docker World";
    }

}
