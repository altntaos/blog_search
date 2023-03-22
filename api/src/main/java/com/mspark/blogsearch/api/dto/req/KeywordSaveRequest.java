package com.mspark.blogsearch.api.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/22
 * @Time : 8:08
 */
@Data
public class KeywordSaveRequest {

    @NotEmpty
    private String keyword;

}
