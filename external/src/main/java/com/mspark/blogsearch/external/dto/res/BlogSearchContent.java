package com.mspark.blogsearch.external.dto.res;

import lombok.Builder;
import lombok.Data;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 15:54
 */
@Data
public class BlogSearchContent {

    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private String datetime;

    @Builder
    public BlogSearchContent(String title, String contents, String url, String blogname, String thumbnail, String datetime) {
        this.title = title;
        this.contents = contents;
        this.url = url;
        this.blogname = blogname;
        this.thumbnail = thumbnail;
        this.datetime = datetime;
    }

    public static BlogSearchContent fromKakaoBlogSearchResponseDocument(KakaoBlogSearchResponseDocument kakaoBlogSearchResponseDocument){
        return BlogSearchContent.builder()
            .title(kakaoBlogSearchResponseDocument.getTitle())
            .contents(kakaoBlogSearchResponseDocument.getContents())
            .url(kakaoBlogSearchResponseDocument.getUrl())
            .blogname(kakaoBlogSearchResponseDocument.getBlogname())
            .thumbnail(kakaoBlogSearchResponseDocument.getThumbnail())
            .datetime(kakaoBlogSearchResponseDocument.getDatetime()).build();
    }

    public static BlogSearchContent fromNaverBlogSearchResponseItem(NaverBlogSearchResponseItem naverBlogSearchResponseItem){
        return BlogSearchContent.builder()
            .title(naverBlogSearchResponseItem.getTitle())
            .contents(naverBlogSearchResponseItem.getDescription())
            .url(naverBlogSearchResponseItem.getLink())
            .blogname(naverBlogSearchResponseItem.getBloggername())
            .thumbnail(naverBlogSearchResponseItem.getBloggerlink())
            .datetime(naverBlogSearchResponseItem.getPostdate()).build();
    }

}
