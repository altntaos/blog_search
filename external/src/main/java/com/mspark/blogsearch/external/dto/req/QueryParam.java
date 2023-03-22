package com.mspark.blogsearch.external.dto.req;

import org.springframework.util.MultiValueMap;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 22:20
 */
public interface QueryParam {

    MultiValueMap<String, String> buildParamMap();
}
