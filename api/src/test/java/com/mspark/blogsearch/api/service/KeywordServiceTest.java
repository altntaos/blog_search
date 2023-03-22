package com.mspark.blogsearch.api.service;

import static com.mspark.blogsearch.api.constants.ApiConstants.KEYWORD_SCORE_KEY;

import com.mspark.blogsearch.api.dto.res.KeywordResponse;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 10:35
 */
@SpringBootTest
public class KeywordServiceTest {

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @BeforeEach
    public void cleanUp() {
        databaseClient.sql("delete from keyword").fetch().rowsUpdated();
        databaseClient.sql("delete from keyword_history").fetch().rowsUpdated();
        reactiveRedisTemplate.opsForZSet().delete(KEYWORD_SCORE_KEY).block();
    }

    @Test
    @DisplayName("신규 keyword가 저장되고 랭크된다")
    public void test_save_keyword_new() {
        String keywordStr = "test";
        KeywordResponse expectedResponse = KeywordResponse.of(keywordStr, 1);

        keywordService.save(keywordStr).as(StepVerifier::create).expectNext(expectedResponse).verifyComplete();

        Map<String, Object> expectedKeywordRowMap = Map.of("id", 1L, "keyword", keywordStr);

        //Check Inserted Row - Keyword
        databaseClient.sql("select * from keyword where keyword = '" + keywordStr + "'")
            .fetch()
            .one()
            .as(StepVerifier::create)
            .expectNext(expectedKeywordRowMap)
            .verifyComplete();

        reactiveRedisTemplate.opsForZSet().score(KEYWORD_SCORE_KEY, keywordStr).as(StepVerifier::create).expectNext(1.0).verifyComplete();
    }


    @Test
    @DisplayName("기존 keyword는 랭크 스코어가 상승한다")
    public void test_save_keyword_old() {
        String keywordStr = "test";
        KeywordResponse expectedResponse = KeywordResponse.of(keywordStr, 2);

        //Given : Set Test data (blocking)
        databaseClient.sql("insert into keyword (keyword) values('test')").fetch().rowsUpdated().block();
        reactiveRedisTemplate.opsForZSet().add(KEYWORD_SCORE_KEY, keywordStr, 1.0).block();


        keywordService.save(keywordStr).as(StepVerifier::create).expectNext(expectedResponse).verifyComplete();

        reactiveRedisTemplate.opsForZSet().score(KEYWORD_SCORE_KEY, keywordStr).as(StepVerifier::create).expectNext(2.0).verifyComplete();
    }

    @Test
    @DisplayName("저장되어 있는 검색어 순위를 사이즈에 맞게 리턴한다")
    public void test_get_ranks(){

        //Given set data (blocking)
        for(int i =1; i < 31; i++){
            reactiveRedisTemplate.opsForZSet().add(KEYWORD_SCORE_KEY, String.valueOf(i), (double)i).block();
        }

        List<KeywordResponse> expectedKeywordRank5
            = List.of(KeywordResponse.of("30", 30),
            KeywordResponse.of("29", 29),
            KeywordResponse.of("28", 28),
            KeywordResponse.of("27", 27),
            KeywordResponse.of("26", 26));

        keywordService.getRanks(5).as(StepVerifier::create).expectNext(expectedKeywordRank5).verifyComplete();

        //delete test data
        reactiveRedisTemplate.opsForZSet().delete(KEYWORD_SCORE_KEY).block();
    }

}
