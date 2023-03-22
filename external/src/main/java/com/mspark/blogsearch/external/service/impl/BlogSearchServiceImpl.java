package com.mspark.blogsearch.external.service.impl;

import com.mspark.blogsearch.external.dto.req.BlogSearchRequest;
import com.mspark.blogsearch.external.dto.res.BlogSearchResponse;
import com.mspark.blogsearch.external.enums.ResultCode;
import com.mspark.blogsearch.external.exception.AllExternalApiDownException;
import com.mspark.blogsearch.external.service.BlogSearchApiService;
import com.mspark.blogsearch.external.service.BlogSearchService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;


/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 17:34
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BlogSearchServiceImpl implements BlogSearchService {

    private final KaKaoExternalService kaKaoWebclientService;
    private final NaverExternalService naverWebclientService;
    private final BlogSearchApiService blogSearchApiService;

    public Mono<BlogSearchResponse> search(String query, String sort, Integer page, Integer size) {
        log.debug("[BlogSearchService.search()][param : query = {}, sort = {}, page = {}, size = {}", query, sort, page, size);
        BlogSearchRequest blogSearchRequest = BlogSearchRequest.of(query, sort, page, size);

        return blogSearchApiService.save(query)
            .flatMap(keywordResponse -> kaKaoWebclientService.searchBlog(blogSearchRequest)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(t -> Exceptions.isRetryExhausted(t),
                    t -> naverWebclientService.searchBlog(blogSearchRequest)
                        .retryWhen(
                            Retry.backoff(3, Duration.ofSeconds(1)).onRetryExhaustedThrow((spec, signal) -> new AllExternalApiDownException(ResultCode.EXTERNAL_API_ERROR)))));
    }

}
