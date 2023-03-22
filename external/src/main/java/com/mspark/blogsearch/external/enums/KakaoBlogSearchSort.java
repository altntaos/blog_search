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
public enum KakaoBlogSearchSort {
    ACC("accuracy"), RC("recency");

    private String sortKey;

    KakaoBlogSearchSort(String sortKey) {
        this.sortKey = sortKey;
    }

}
