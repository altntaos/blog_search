package com.mspark.blogsearch.external.dto.req;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/19
 * @Time : 16:19
 */
@Data
public class KakaoBlogSearchQueryParam implements QueryParam {

    private String queryStr;

    private String sort;

    private Integer page;

    private Integer size;

    protected KakaoBlogSearchQueryParam() {}

    @Builder
    public KakaoBlogSearchQueryParam(String queryStr, String sort, Integer page, Integer size) {
        this.queryStr = queryStr;
        this.sort = sort;
        this.page = page;
        this.size = size;
    }

    public static KakaoBlogSearchQueryParam fromRequest(BlogSearchRequest request) {
        return KakaoBlogSearchQueryParam.builder()
            .queryStr(request.getQueryStr())
            .sort(request.getBlogSearchSort().getKakaoBlogSearchSort().getSortKey())
            .page(request.getPage())
            .size(request.getSize())
            .build();
    }

    @Override
    public MultiValueMap<String, String> buildParamMap(){
        MultiValueMap paramMap = new LinkedMultiValueMap();

        paramMap.add("query", this.queryStr);
        paramMap.add("sort", this.sort);
        paramMap.add("page", this.page.toString());
        paramMap.add("size", this.size.toString());

        return paramMap;
    }

}
