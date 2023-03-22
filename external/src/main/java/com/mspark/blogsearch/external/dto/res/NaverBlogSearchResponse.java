package com.mspark.blogsearch.external.dto.res;

import java.util.List;
import lombok.Data;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 15:44
 */
@Data
public class NaverBlogSearchResponse {

    private Integer total;
    private Integer start;
    private Integer display;
    private List<NaverBlogSearchResponseItem> items;

}
