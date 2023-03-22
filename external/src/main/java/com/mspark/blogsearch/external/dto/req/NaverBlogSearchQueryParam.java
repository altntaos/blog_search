package com.mspark.blogsearch.external.dto.req;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/19
 * @Time : 17:29
 */
@Data
public class NaverBlogSearchQueryParam implements QueryParam {

    private String queryStr;
    private Integer display;
    private Integer start;
    private String sort;

    protected NaverBlogSearchQueryParam() {}

    @Builder
    public NaverBlogSearchQueryParam(String queryStr, Integer display, Integer start, String sort) {
        this.queryStr = queryStr;
        this.display = display;
        this.start = start;
        this.sort = sort;
    }

    public static NaverBlogSearchQueryParam fromRequest(BlogSearchRequest request){
        return NaverBlogSearchQueryParam.builder()
            .queryStr(request.getQueryStr())
            .sort(request.getBlogSearchSort().getNaverBlogSearchSort().getSortKey())
            .display(request.getSize())
            .start((request.getPage()-1) * request.getSize() + 1).build();
    }

    @Override
    public MultiValueMap<String, String> buildParamMap() {
        MultiValueMap paramMap = new LinkedMultiValueMap();

        paramMap.add("query", this.queryStr);
        paramMap.add("display", this.display.toString());
        paramMap.add("start", this.start.toString());
        paramMap.add("sort", this.sort);

        return paramMap;
    }
}
