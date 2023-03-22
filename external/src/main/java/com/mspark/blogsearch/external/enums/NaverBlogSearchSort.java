package com.mspark.blogsearch.external.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 15:51
 */
@ToString
@Getter
public enum NaverBlogSearchSort {
    ACC("sim"), RC("date");

    private String sortKey;

    NaverBlogSearchSort(String sortKey) {
        this.sortKey = sortKey;
    }

}
