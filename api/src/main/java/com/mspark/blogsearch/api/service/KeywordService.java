package com.mspark.blogsearch.api.service;

import com.mspark.blogsearch.api.dto.res.KeywordResponse;
import java.util.List;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 15:00
 */
public interface KeywordService {

    Mono<KeywordResponse> save(String keyword);

    Mono<List<KeywordResponse>> getRanks(Integer size);
}
