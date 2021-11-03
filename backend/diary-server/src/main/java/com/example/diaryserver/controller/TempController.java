package com.example.diaryserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/temp")
@RestController
public class TempController {

    @GetMapping("/test")
    public String test() {
        return "다이어리";
    }
    

}
