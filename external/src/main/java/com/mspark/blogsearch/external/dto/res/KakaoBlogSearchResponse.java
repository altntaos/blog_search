package com.mspark.blogsearch.external.dto.res;

import java.util.List;
import lombok.Data;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/19
 * @Time : 16:21
 */
@Data
public class KakaoBlogSearchResponse {

    private KakaoBlogSearchResponseMeta meta;

    private List<KakaoBlogSearchResponseDocument> documents;
}
