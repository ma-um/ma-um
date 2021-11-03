package com.spuit.maum.authserver.controller;

import com.spuit.maum.authserver.dto.BaseResponseBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<? extends BaseResponseBody> test() {
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "OK"));
    }




}
