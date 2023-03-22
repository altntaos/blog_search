package com.mspark.blogsearch.api.controller;

import com.mspark.blogsearch.api.dto.req.KeywordSaveRequest;
import com.mspark.blogsearch.api.dto.res.KeywordResponse;
import com.mspark.blogsearch.api.service.KeywordService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 15:02
 */
@RestController
@RequestMapping("/api/keyword")
@RequiredArgsConstructor
public class KeywordController {

    private final KeywordService keywordService;

    @PostMapping
    public Mono<ResponseEntity<KeywordResponse>> save(@RequestBody @Valid KeywordSaveRequest keywordSaveRequest){
        return keywordService.save(keywordSaveRequest.getKeyword()).map(ResponseEntity::ok);
    }

    @GetMapping("/ranks")
    public Mono<ResponseEntity<List<KeywordResponse>>> ranks(@RequestParam(required = false, defaultValue = "10") Integer size){
        return keywordService.getRanks(size).map(ResponseEntity::ok);
    }

}
