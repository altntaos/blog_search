package com.mspark.blogsearch.external.service.impl;

import com.mspark.blogsearch.external.dto.req.BlogSearchRequest;
import com.mspark.blogsearch.external.dto.req.KakaoBlogSearchQueryParam;
import com.mspark.blogsearch.external.dto.res.BlogSearchResponse;
import com.mspark.blogsearch.external.dto.res.KakaoBlogSearchResponse;
import com.mspark.blogsearch.external.service.ExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 11:12
 */
@Component
@RequiredArgsConstructor
public class KaKaoExternalService implements ExternalService {

    private final WebClient kakaoWebClient;
    @Value("${external-search-api.kakao.search-path.blog}")
    private String kakaoBlogSearchPath;

    @Override
    public Mono<BlogSearchResponse> searchBlog(BlogSearchRequest request) {

        return kakaoWebClient
            .get()
            .uri(uriBuilder -> uriBuilder.path(kakaoBlogSearchPath)
                .queryParams(KakaoBlogSearchQueryParam.fromRequest(request).buildParamMap()).build())
            .retrieve()
            .bodyToMono(KakaoBlogSearchResponse.class)
            .map(BlogSearchResponse::fromKakaoBlogSearchResponse);
    }
}
