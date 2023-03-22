package com.mspark.blogsearch.external.dto.res;

import com.mspark.blogsearch.external.enums.ExternalApiSource;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 15:51
 */
@Data
public class BlogSearchResponse {

    private String source;
    private Integer totalCount;
    private List<BlogSearchContent> contents;

    @Builder
    public BlogSearchResponse(String source, Integer totalCount, List<BlogSearchContent> contents) {
        this.source = source;
        this.totalCount = totalCount;
        this.contents = contents;
    }

    public static BlogSearchResponse fromKakaoBlogSearchResponse(KakaoBlogSearchResponse kakaoBlogSearchResponse) {

        return BlogSearchResponse.builder()
            .source(ExternalApiSource.KAKAO.name())
            .totalCount(kakaoBlogSearchResponse.getMeta().getPageableCount())
            .contents(kakaoBlogSearchResponse.getDocuments()
                                            .stream()
                                            .map(BlogSearchContent::fromKakaoBlogSearchResponseDocument)
                                            .collect(Collectors.toList()))
            .build();
    }

    public static BlogSearchResponse fromNaverBlogSearchResponse(NaverBlogSearchResponse naverBlogSearchResponse){
        return BlogSearchResponse.builder()
            .source(ExternalApiSource.NAVER.name())
            .totalCount(naverBlogSearchResponse.getTotal())
            .contents(naverBlogSearchResponse.getItems()
                .stream()
                .map(BlogSearchContent::fromNaverBlogSearchResponseItem)
                .collect(Collectors.toList()))
            .build();
    }


}
