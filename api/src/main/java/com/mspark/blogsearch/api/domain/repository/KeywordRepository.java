package com.mspark.blogsearch.api.domain.repository;

import com.mspark.blogsearch.api.domain.entity.Keyword;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 14:59
 */
public interface KeywordRepository extends ReactiveCrudRepository<Keyword, Long> {

    Mono<Keyword> findByKeyword(String keyword);
}
