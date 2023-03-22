package com.mspark.blogsearch.api.redis;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Range.Bound;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import reactor.test.StepVerifier;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 16:15
 */
@DataRedisTest
public class RedisTest {

    @Autowired
    private ReactiveRedisTemplate <String, String> reactiveRedisTemplate;

    @Test
    @DisplayName("redis SortedSet을 이용하여 score로 정렬된 값을 추출할 수 있다")
    public void test_ops_sorted_set(){
        String key = "popularKeywordTest";

        ReactiveZSetOperations<String, String> zSetOperations = reactiveRedisTemplate.opsForZSet();

        List<TypedTuple<String>> tuples = new ArrayList<>();

        // Sync action delete test data
        zSetOperations.delete(key).block();

        for(int i = 1; i < 11; i++){
            tuples.add(new DefaultTypedTuple<>(String.valueOf(i), (double) i));
        }

        //Add Test Data 10 tuples with score 1 ~ 10
        zSetOperations.addAll(key, tuples).as(StepVerifier::create).expectNext(10L).verifyComplete();

        //Increase Id : 8 (value + 1) * 3
        zSetOperations.incrementScore(key, "8", 1).as(StepVerifier::create).expectNext(9.0).verifyComplete();
        zSetOperations.incrementScore(key, "8", 1).as(StepVerifier::create).expectNext(10.0).verifyComplete();
        zSetOperations.incrementScore(key, "8", 1).as(StepVerifier::create).expectNext(11.0).verifyComplete();

        //Insert new key and increase value
        zSetOperations.incrementScore(key, "11", 1).as(StepVerifier::create).expectNext(1.0).verifyComplete();
        zSetOperations.incrementScore(key, "12", 1).as(StepVerifier::create).expectNext(1.0).verifyComplete();

        //get tuples sort by score, value desc 10
        zSetOperations.reverseRangeWithScores(key, Range.<Long>from(Bound.inclusive(0L)).to(Bound.inclusive(9L)))
            .as(StepVerifier::create)
            .expectNext(new DefaultTypedTuple<>("8", 11.0))
            .expectNext(new DefaultTypedTuple<>("10", 10.0))
            .expectNext(new DefaultTypedTuple<>("9", 9.0))
            .expectNext(new DefaultTypedTuple<>("7", 7.0))
            .expectNext(new DefaultTypedTuple<>("6", 6.0))
            .expectNext(new DefaultTypedTuple<>("5", 5.0))
            .expectNext(new DefaultTypedTuple<>("4", 4.0))
            .expectNext(new DefaultTypedTuple<>("3", 3.0))
            .expectNext(new DefaultTypedTuple<>("2", 2.0))
            .expectNext(new DefaultTypedTuple<>("12", 1.0))
            .verifyComplete();
    }
}
