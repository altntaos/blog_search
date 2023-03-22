package com.mspark.blogsearch.external.dto.res;

import lombok.Data;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 15:45
 */
@Data
public class NaverBlogSearchResponseItem {

    private String title;
    private String link;
    private String description;
    private String bloggername;
    private String bloggerlink;
    private String postdate;

}
