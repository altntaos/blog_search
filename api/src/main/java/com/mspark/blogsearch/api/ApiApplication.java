package com.mspark.blogsearch.api;

import static com.mspark.blogsearch.api.constants.ApiConstants.KEYWORD_SCORE_KEY;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 11:41
 */
@SpringBootApplication
@RequiredArgsConstructor
public class ApiApplication {

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void testRanksInsert() {
        List<TypedTuple<String>> testRanks = List.of(TypedTuple.of("계란찜 만들기", 2.0)
            ,TypedTuple.of("자동차", 4.0),
            TypedTuple.of("통영숙소", 5.0),
            TypedTuple.of("국내 벛꽃여행지 추천", 8.0),
            TypedTuple.of("정자역 맛집 추천", 1.0));

        reactiveRedisTemplate.opsForZSet().addAll(KEYWORD_SCORE_KEY, testRanks).block();
    }
    @EventListener(ContextClosedEvent.class)
    public void testRanksDelete(){
        reactiveRedisTemplate.opsForZSet().delete(KEYWORD_SCORE_KEY).block();
    }
}
