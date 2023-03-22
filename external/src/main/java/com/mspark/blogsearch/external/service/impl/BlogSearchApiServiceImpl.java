package com.mspark.blogsearch.external.service.impl;

import com.mspark.blogsearch.external.dto.res.KeywordResponse;
import com.mspark.blogsearch.external.enums.ResultCode;
import com.mspark.blogsearch.external.exception.ApiServerException;
import com.mspark.blogsearch.external.service.BlogSearchApiService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 7:05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BlogSearchApiServiceImpl implements BlogSearchApiService {

    private final WebClient apiWebClient;
    @Value("${api.path.keyword}")
    private String keywordPath;

    @Override
    public Mono<KeywordResponse> save(String keyword) {
        return apiWebClient.post()
            .uri(uriBuilder -> uriBuilder.path(keywordPath).build()).body(BodyInserters.fromValue(Map.of("keyword", keyword)))
            .retrieve()
            .bodyToMono(KeywordResponse.class)
            .doOnError(ex -> {
                log.warn(ex.getMessage());
                throw new ApiServerException(ResultCode.API_ERROR);
            });
    }
}
