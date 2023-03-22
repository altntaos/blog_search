package com.mspark.blogsearch.api.dto.res;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 17:50
 */
@Data
public class KeywordResponse {

    private String keyword;
    private Integer score;

    @Builder
    public KeywordResponse(String keyword, Integer score) {
        this.keyword = keyword;
        this.score = score;
    }

    public static KeywordResponse of(String keyword, Integer score){
        return KeywordResponse.builder().keyword(keyword).score(score).build();
    }

    public static KeywordResponse fromKeywordTuple(TypedTuple<String> keywordTuple){
        return KeywordResponse.builder().keyword(keywordTuple.getValue()).score(keywordTuple.getScore().intValue()).build();
    }

}
