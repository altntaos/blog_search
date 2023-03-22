package com.mspark.blogsearch.external.dto.req;

import com.mspark.blogsearch.external.enums.BlogSearchSort;
import lombok.Builder;
import lombok.Data;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 16:15
 */
@Data
public class BlogSearchRequest {

    private String queryStr;
    private BlogSearchSort blogSearchSort;
    private Integer page;
    private Integer size;

    @Builder
    public BlogSearchRequest(String queryStr, BlogSearchSort blogSearchSort, Integer page, Integer size) {
        this.queryStr = queryStr;
        this.blogSearchSort = blogSearchSort;
        this.page = page;
        this.size = size;
    }

    public static BlogSearchRequest of(String query, String sort, Integer page, Integer size){
        return BlogSearchRequest.builder()
            .queryStr(query)
            .blogSearchSort(BlogSearchSort.get(sort))
            .page(page)
            .size(size)
            .build();
    }
}
