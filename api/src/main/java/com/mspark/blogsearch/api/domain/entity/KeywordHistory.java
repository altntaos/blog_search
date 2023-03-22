package com.mspark.blogsearch.api.domain.entity;

import java.time.LocalDateTime;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 19:24
 */
@Table(name = "keyword_history")
public class KeywordHistory {

    @Id
    private Long id;

    @Column(value = "keyword_id")
    private Long keywordId;
    @Column(value = "searching_date")
    private LocalDateTime searchingDate;

    @Builder
    public KeywordHistory(Long id, Long keywordId, LocalDateTime searchingDate) {
        this.id = id;
        this.keywordId = keywordId;
        this.searchingDate = searchingDate;
    }

    public static KeywordHistory fromKeyword(Keyword keyword){
        return KeywordHistory.builder().keywordId(keyword.getId()).searchingDate(LocalDateTime.now()).build();
    }

}
