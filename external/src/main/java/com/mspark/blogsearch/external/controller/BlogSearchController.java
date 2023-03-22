package com.mspark.blogsearch.external.controller;

import com.mspark.blogsearch.external.dto.res.BlogSearchResponse;
import com.mspark.blogsearch.external.enums.ResultCode;
import com.mspark.blogsearch.external.exception.InvalidSearchQueryParamException;
import com.mspark.blogsearch.external.service.BlogSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/19
 * @Time : 16:28
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/external")
public class BlogSearchController {

    private final BlogSearchService blogSearchService;

    @GetMapping("/search")
    public Mono<ResponseEntity<BlogSearchResponse>> search(@RequestParam(required = true) String query,
        @RequestParam(required = false, defaultValue = "ACC") String sort,
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer size) {

        if(page > 50 || page < 1){
            throw new InvalidSearchQueryParamException(ResultCode.INVALID_PARAM, "page", "page must be between 1 and 50");
        }

        if(size > 50 || size < 1){
            throw new InvalidSearchQueryParamException(ResultCode.INVALID_PARAM, "size", "size must be between 1 and 50");
        }

        return blogSearchService.search(query, sort, page, size).map(ResponseEntity::ok);
    }
}
