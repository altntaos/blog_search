package com.mspark.blogsearch.external.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/19
 * @Time : 16:22
 */
@Data
public class KakaoBlogSearchResponseMeta {

    @JsonProperty("total_count")
    private Integer totalCount;
    @JsonProperty("pageable_count")
    private Integer pageableCount;
    @JsonProperty("is_end")
    private Boolean isEnd;

}
