package com.mspark.blogsearch.api.repository;

import com.mspark.blogsearch.api.domain.entity.KeywordHistory;
import com.mspark.blogsearch.api.domain.repository.KeywordHistoryRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 20:00
 */
@DataR2dbcTest
public class KeywordHistoryRepositoryTest {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private KeywordHistoryRepository keywordHistoryRepository;

    @BeforeEach
    public void cleanUp(){
        databaseClient.sql("delete from keyword_history").fetch().rowsUpdated().block();
    }

    @Test
    @DisplayName("keywordHistory entity가 저장된다")
    public void test_save_keyword_hitory_success(){
        KeywordHistory keywordHistory = KeywordHistory.builder().keywordId(1L).searchingDate(LocalDateTime.now()).build();

        keywordHistoryRepository.save(keywordHistory).as(StepVerifier::create).expectNextCount(1).verifyComplete();
    }


}
