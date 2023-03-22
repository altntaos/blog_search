package com.mspark.blogsearch.api.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.mspark.blogsearch.api.dto.res.KeywordResponse;
import com.mspark.blogsearch.api.service.KeywordService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 10:44
 */
@WebFluxTest(KeywordController.class)
public class KeywordControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private KeywordService mockKeywordService;

    @Test
    @DisplayName("keyword 저장 파라미터 유효성 테스트")
    public void test_save_keyword_param() {
        String savePath = "/api/keyword";

        KeywordResponse expectedResponse = KeywordResponse.of("test", 1);
        given(mockKeywordService.save(anyString())).willReturn(Mono.just(expectedResponse));

        //Without body
        webTestClient.post()
            .uri(uriBuilder -> uriBuilder.path(savePath).build())
            .exchange()
            .expectStatus().isBadRequest();

        //With body but empty String
        webTestClient.post()
            .uri(uriBuilder -> uriBuilder.path(savePath).build())
            .body(BodyInserters.fromValue(Map.of("keyword", "")))
            .exchange()
            .expectStatus().isBadRequest();

        //With valid parameter
        webTestClient.post()
            .uri(uriBuilder -> uriBuilder.path(savePath).build())
            .body(BodyInserters.fromValue(Map.of("keyword", "test")))
            .exchange()
            .expectStatus().isOk()
            .expectBody(KeywordResponse.class).isEqualTo(expectedResponse);

    }

    @Test
    @DisplayName("랭킹 조회 api 테스트 ")
    public void test_call_ranks(){
        String ranksPath = "/api/keyword/ranks";
        List<KeywordResponse> ranks10 = new ArrayList<>();
        List<KeywordResponse> ranks5 = new ArrayList<>();

        given(mockKeywordService.getRanks(10)).willReturn(Mono.just(ranks10));
        given(mockKeywordService.getRanks(5)).willReturn(Mono.just(ranks5));

        //Call without param 'size' return default size 10 ranks
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path(ranksPath).build())
            .exchange()
            .expectStatus().isOk()
            .expectBody(List.class).isEqualTo(ranks10);

        //Call with param 'size'
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path(ranksPath).queryParam("size", 5).build())
            .exchange()
            .expectStatus().isOk()
            .expectBody(List.class).isEqualTo(ranks10);

        //Call with param 'size' greater then 10
        webTestClient.get()
            .uri(uriBuilder -> uriBuilder.path(ranksPath).queryParam("size", 11).build())
            .exchange()
            .expectStatus().isBadRequest();
    }

}
