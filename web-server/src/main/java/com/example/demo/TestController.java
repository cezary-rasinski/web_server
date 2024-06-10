package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //it means this class will act as a controller
//so later spring knows this class contains control commands
public class TestController {

    //ZAD1: Napisz aplikację, która uruchomi serwer webowy, który po połączeniu wyświetli napis “Hello World”.
    @GetMapping("hello") //service based on GET
    public String test(){
        return "hello world";
    }
    //such structure returns(whatever our code returns) under a link
    //to check type in browser: localhost(my_sever_number)/(string from get mapping)
}
