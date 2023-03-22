package com.mspark.blogsearch.external.dto.res;

import lombok.Data;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/19
 * @Time : 16:22
 */
@Data
public class KakaoBlogSearchResponseDocument {

    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private String datetime;

}
