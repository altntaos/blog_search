package com.mspark.blogsearch.external.service;

import com.mspark.blogsearch.external.dto.res.BlogSearchResponse;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 17:33
 */
public interface BlogSearchService {

    Mono<BlogSearchResponse> search(String query, String sort, Integer page, Integer size);
}
