package com.mspark.blogsearch.api.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 14:57
 */
@Table(name = "keyword")
@Getter @Setter
@ToString
public class Keyword {

    @Id
    private Long id;

    private String keyword;

    @Builder
    public Keyword(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public static Keyword of(String keyword){
        return Keyword.builder().keyword(keyword).build();
    }
}
