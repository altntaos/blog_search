package com.mspark.blogsearch.api.controller;

import com.mspark.blogsearch.api.service.KeywordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 10:44
 */
@WebFluxTest(KeywordControllerTest.class)
public class KeywordControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private KeywordService keywordService;

    @Test
    @DisplayName("keyword 저장 파라미터 유효성 테스트")
    public void test_save_keyword_param(){

    }

}
