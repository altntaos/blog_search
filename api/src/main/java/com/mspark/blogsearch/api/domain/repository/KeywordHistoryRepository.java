package com.mspark.blogsearch.api.domain.repository;

import com.mspark.blogsearch.api.domain.entity.KeywordHistory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 19:31
 */
public interface KeywordHistoryRepository extends ReactiveCrudRepository<KeywordHistory, Long> {

}
