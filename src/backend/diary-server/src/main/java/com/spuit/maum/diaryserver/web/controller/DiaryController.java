package com.spuit.maum.diaryserver.web.controller;

import com.spuit.maum.diaryserver.web.request.DiaryWriteRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Diary 관련 api Rest controller.
 *
 * @author cherrytomato1
 * @version 1.0.0
 */

@RestController
@RequestMapping("/api/v1/diary")
@CrossOrigin("*")
public class DiaryController {

}
