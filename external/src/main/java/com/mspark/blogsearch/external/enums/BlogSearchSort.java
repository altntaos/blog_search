package com.mspark.blogsearch.external.enums;

import java.util.Arrays;
import lombok.Getter;
import lombok.ToString;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 15:58
 */
@ToString
@Getter
public enum BlogSearchSort {

    ACC("정확도순", KakaoBlogSearchSort.ACC, NaverBlogSearchSort.ACC),
    RC("최신순", KakaoBlogSearchSort.RC, NaverBlogSearchSort.RC);

    BlogSearchSort(String description, KakaoBlogSearchSort kakaoBlogSearchSort, NaverBlogSearchSort naverBlogSearchSort) {
        this.description = description;
        this.kakaoBlogSearchSort = kakaoBlogSearchSort;
        this.naverBlogSearchSort = naverBlogSearchSort;
    }

    private String description;
    private KakaoBlogSearchSort kakaoBlogSearchSort;
    private NaverBlogSearchSort naverBlogSearchSort;

    public static BlogSearchSort get(String sort) {
        return Arrays.stream(values()).filter(blogSearchSort ->
            blogSearchSort.name().equals(sort)
        ).findAny().orElse(BlogSearchSort.ACC);
    }

}
