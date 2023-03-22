package com.mspark.blogsearch.external.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.mspark.blogsearch.external.dto.res.BlogSearchResponse;
import com.mspark.blogsearch.external.dto.res.KeywordResponse;
import com.mspark.blogsearch.external.enums.ExternalApiSource;
import com.mspark.blogsearch.external.enums.ResultCode;
import com.mspark.blogsearch.external.exception.ApiServerException;
import com.mspark.blogsearch.external.service.impl.KaKaoExternalService;
import com.mspark.blogsearch.external.service.impl.NaverExternalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 8:12
 */
@SpringBootTest
public class BlogSearchServiceTest {

    @Autowired
    private BlogSearchService blogSearchService;
    @MockBean
    private KaKaoExternalService mockKaKaoExternalService;
    @MockBean
    private NaverExternalService mockNaverExternalService;
    @MockBean
    private BlogSearchApiService mockBlogSearchApiService;

    @Test
    @DisplayName("모든 API가 정상이면 Kakao API를 통해 검색 한다")
    public void test_blog_search_all_api_available(){
        KeywordResponse mockKeywordResponse = KeywordResponse.builder().keyword("test").score(100).build();
        BlogSearchResponse mockKakaoResponse = BlogSearchResponse.builder().source(ExternalApiSource.KAKAO.name()).build();
        BlogSearchResponse mockNaverResponse = BlogSearchResponse.builder().source(ExternalApiSource.NAVER.name()).build();

        given(mockBlogSearchApiService.save(any())).willReturn(Mono.just(mockKeywordResponse));
        given(mockKaKaoExternalService.searchBlog(any())).willReturn(Mono.just(mockKakaoResponse));
        given(mockNaverExternalService.searchBlog(any())).willReturn(Mono.just(mockNaverResponse));

        Mono<BlogSearchResponse> blogSearchResponseMono = blogSearchService.search("test", "ACC", 1, 10);

        StepVerifier.create(blogSearchResponseMono).expectNext(mockKakaoResponse).verifyComplete();

    }

    @Test
    @DisplayName("Kakao API가 사용 불가능 하면 Naver API를 통해 검색한다")
    public void test_blog_search_kakao_api_unavailable(){
        KeywordResponse mockKeywordResponse = KeywordResponse.builder().keyword("test").score(100).build();
        BlogSearchResponse mockNaverResponse = BlogSearchResponse.builder().source(ExternalApiSource.NAVER.name()).build();

        given(mockBlogSearchApiService.save(any())).willReturn(Mono.just(mockKeywordResponse));
        given(mockKaKaoExternalService.searchBlog(any())).willReturn(Mono.error(new RuntimeException()));
        given(mockNaverExternalService.searchBlog(any())).willReturn(Mono.just(mockNaverResponse));

        Mono<BlogSearchResponse> blogSearchResponseMono = blogSearchService.search("test", "ACC", 1, 10);

        StepVerifier.create(blogSearchResponseMono).expectNext(mockNaverResponse).verifyComplete();
    }

    @Test
    @DisplayName("API 서버가 예외를 던지면 검색이 실행되지 않는다")
    public void test_api_server_error(){
        given(mockBlogSearchApiService.save(any())).willReturn(Mono.error(new ApiServerException(ResultCode.API_ERROR)));

        Mono<BlogSearchResponse> blogSearchResponseMono = blogSearchService.search("test", "ACC", 1, 10);

        StepVerifier.create(blogSearchResponseMono).expectError();
    }
}
