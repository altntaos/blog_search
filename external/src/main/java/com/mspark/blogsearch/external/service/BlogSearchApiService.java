package com.mspark.blogsearch.external.service;

import com.mspark.blogsearch.external.dto.res.KeywordResponse;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 7:04
 */
public interface BlogSearchApiService {

    Mono<KeywordResponse> save(String keyword);
}
