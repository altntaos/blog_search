package com.mspark.blogsearch.external.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.mspark.blogsearch.external.dto.res.BlogSearchContent;
import com.mspark.blogsearch.external.dto.res.BlogSearchResponse;
import com.mspark.blogsearch.external.service.BlogSearchService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 7:41
 */
@WebFluxTest(BlogSearchController.class)
public class BlogSearchControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BlogSearchService mockBlogSearchService;

    @Test
    @DisplayName("블로그 검색 파라미터 유효성 테스트")
    public void test_search_blog_query_param() {

        String blogSearchPath = "/external/search";

        BlogSearchResponse mockBlogSearchResponse
            = BlogSearchResponse.builder().totalCount(1).contents(List.of(BlogSearchContent.builder().title("test").build())).build();

        given(mockBlogSearchService.search(anyString(), anyString(), anyInt(), anyInt())).willReturn(Mono.just(mockBlogSearchResponse));

        //Without required query param 'query'
        webTestClient.get()
            .uri(blogSearchPath)
            .exchange()
            .expectStatus()
            .isBadRequest();

        //Without invalid query param over 50 'page'
        webTestClient.get()
            .uri(uriBuilder ->
                uriBuilder.path(blogSearchPath).queryParam("query", "test")
                    .queryParam("page", 100).build()
            )
            .exchange()
            .expectStatus()
            .isBadRequest();

        //Without invalid query param over 50 'size'
        webTestClient.get()
            .uri(uriBuilder ->
                uriBuilder.path(blogSearchPath).queryParam("query", "test")
                    .queryParam("size", 100).build()
            )
            .exchange()
            .expectStatus()
            .isBadRequest();

        //With required query param
        webTestClient.get()
            .uri(uriBuilder ->
                uriBuilder.path(blogSearchPath).queryParam("query", "test").build()
            )
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(BlogSearchResponse.class)
            .isEqualTo(mockBlogSearchResponse);
    }
}

