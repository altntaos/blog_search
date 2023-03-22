package com.mspark.blogsearch.api.repository;

import com.mspark.blogsearch.api.domain.entity.Keyword;
import com.mspark.blogsearch.api.domain.repository.KeywordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 15:03
 */
@DataR2dbcTest
public class KeywordRepositoryTest {

    @Autowired
    private DatabaseClient databaseClient;
    @Autowired
    private KeywordRepository keywordRepository;

    @BeforeEach
    public void cleanUp(){
        databaseClient.sql("delete from search_keyword").fetch().rowsUpdated().block();
    }

    @Test
    public void test_context_loaded(){
        Assertions.assertNotNull(databaseClient);
        Assertions.assertNotNull(keywordRepository);
    }

    @Test
    @DisplayName("keyword entity가 저장된다")
    public void test_save_keyword_success(){
        Mono<Keyword> searchKeywordMono = keywordRepository.save(Keyword.of("test"));

        StepVerifier.create(searchKeywordMono)
            .expectNextCount(1)
            .verifyComplete();

    }

    @Test
    @DisplayName("keyword 유니크 제약조건 위배시 예외가 발생한다")
    public void test_save_duplicated_keyword_expected_exception(){
        String keyword = "test";

        keywordRepository.save(Keyword.of(keyword))
            .as(StepVerifier::create)
            .expectNextCount(1)
            .verifyComplete();

        keywordRepository.save(Keyword.of(keyword))
            .as(StepVerifier::create)
            .expectErrorMatches(ex -> ex instanceof DuplicateKeyException)
            .verify();

    }

    @Test
    @DisplayName("저장된 keyword로 엔티티를 조회할 수 있다")
    public void test_find_by_keyword(){
        String keyword = "test";

        keywordRepository.save(Keyword.of(keyword))
            .as(StepVerifier::create)
            .expectNextCount(1)
            .verifyComplete();

        keywordRepository.findByKeyword(keyword)
            .as(StepVerifier::create)
            .expectNextCount(1)
            .verifyComplete();
    }
}
