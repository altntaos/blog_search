package com.mspark.blogsearch.external.dto.res;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 17:50
 */
@Data
@NoArgsConstructor
public class KeywordResponse {

    private String keyword;
    private Integer score;

    @Builder
    public KeywordResponse(String keyword, Integer score) {
        this.keyword = keyword;
        this.score = score;
    }
}
