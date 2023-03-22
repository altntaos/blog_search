package com.mspark.blogsearch.external.service.impl;

import com.mspark.blogsearch.external.dto.res.NaverBlogSearchResponse;
import com.mspark.blogsearch.external.service.ExternalService;
import com.mspark.blogsearch.external.dto.req.BlogSearchRequest;
import com.mspark.blogsearch.external.dto.req.NaverBlogSearchQueryParam;
import com.mspark.blogsearch.external.dto.res.BlogSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 11:55
 */
@Component
@RequiredArgsConstructor
public class NaverExternalService implements ExternalService {

    private final WebClient naverWebClient;

    @Value("${external-search-api.naver.search-path.blog}")
    private String naverBlogSearchPath;

    @Override
    public Mono<BlogSearchResponse> searchBlog(BlogSearchRequest request) {

        return naverWebClient.get()
            .uri(uriBuilder -> uriBuilder.path(naverBlogSearchPath)
                .queryParams(NaverBlogSearchQueryParam.fromRequest(request).buildParamMap()).build())
            .retrieve()
            .bodyToMono(NaverBlogSearchResponse.class)
            .map(BlogSearchResponse::fromNaverBlogSearchResponse);
    }
}
