package com.mspark.blogsearch.external.service;

import com.mspark.blogsearch.external.dto.req.BlogSearchRequest;
import com.mspark.blogsearch.external.dto.res.BlogSearchResponse;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/19
 * @Time : 16:31
 */
public interface ExternalService {

    Mono<BlogSearchResponse> searchBlog(BlogSearchRequest request);

}
