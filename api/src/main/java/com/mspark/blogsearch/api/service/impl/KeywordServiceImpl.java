package com.mspark.blogsearch.api.service.impl;

import static com.mspark.blogsearch.api.constants.ApiConstants.KEYWORD_SCORE_KEY;

import com.google.common.collect.Lists;
import com.mspark.blogsearch.api.domain.entity.Keyword;
import com.mspark.blogsearch.api.domain.entity.KeywordHistory;
import com.mspark.blogsearch.api.domain.repository.KeywordHistoryRepository;
import com.mspark.blogsearch.api.domain.repository.KeywordRepository;
import com.mspark.blogsearch.api.dto.res.KeywordResponse;
import com.mspark.blogsearch.api.enums.ResultCode;
import com.mspark.blogsearch.api.exception.ApiDatabaseException;
import com.mspark.blogsearch.api.exception.ApiRedisException;
import com.mspark.blogsearch.api.service.KeywordService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Range.Bound;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 15:00
 */
@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;
    private final KeywordHistoryRepository keywordHistoryRepository;
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    @Override
    @Transactional
    public Mono<KeywordResponse> save(String keyword) {
        return keywordRepository.findByKeyword(keyword)
            .switchIfEmpty(keywordRepository.save(Keyword.of(keyword)))
            .onErrorResume(ex -> {
                if (ex instanceof DuplicateKeyException) {
                    return keywordRepository.findByKeyword(keyword);
                }
                return Mono.error(new ApiDatabaseException(ResultCode.API_ERROR, ex.getMessage()));
            })
            .flatMap(searchKeyword -> keywordHistoryRepository.save(KeywordHistory.fromKeyword(searchKeyword))
                .onErrorResume(ex -> Mono.error(new ApiDatabaseException(ResultCode.API_ERROR, ex.getMessage()))))
            .flatMap(history -> reactiveRedisTemplate.opsForZSet().incrementScore(KEYWORD_SCORE_KEY, keyword, 1.0)
                .onErrorResume(ex -> Mono.error(new ApiRedisException(ResultCode.API_ERROR, ex.getMessage(), KEYWORD_SCORE_KEY))))
            .map(score -> KeywordResponse.of(keyword, score.intValue()));
    }

    @Override
    public Mono<List<KeywordResponse>> getRanks(Integer size) {
        return reactiveRedisTemplate.opsForZSet()
            .reverseRangeWithScores(KEYWORD_SCORE_KEY, Range.<Long>from(Bound.inclusive(0L)).to(Bound.inclusive(size.longValue() - 1)))
            .collectList()
            .map(typedTuples -> typedTuples.stream().map(KeywordResponse::fromKeywordTuple).collect(Collectors.toList()))
            .switchIfEmpty(Mono.just(Lists.newArrayList()))
            .onErrorResume(ex -> Mono.error(new ApiRedisException(ResultCode.API_ERROR, ex.getMessage(), KEYWORD_SCORE_KEY)));
    }
}
