package com.mspark.blogsearch.external.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 15:07
 */
@Slf4j
@Configuration
public class WebClientConfig {

    @Value("${external-search-api.kakao.rest-key}")
    private String kakaoRestKey;
    @Value("${external-search-api.kakao.base-url}")
    private String kakaoBaseUrl;

    @Value("${external-search-api.naver.base-url}")
    private String naverBaseUrl;
    @Value("${external-search-api.naver.id}")
    private String naverAppId;
    @Value("${external-search-api.naver.secret}")
    private String naverAppSecret;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    @Bean
    @Qualifier("kakaoWebClient")
    public WebClient kakaoWebClient() {
        return WebClient.builder()
            .baseUrl(kakaoBaseUrl)
            .filters(exchangeFilterFunctions -> {
                exchangeFilterFunctions.add(logRequest("kakaoWebClient"));
                exchangeFilterFunctions.add(logResponse("kakaoWebClient"));
            })
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("Authorization", kakaoRestKey)
            .build();
    }

    @Bean
    @Qualifier("naverWebClient")
    public WebClient naverWebClient() {
        return WebClient.builder()
            .baseUrl(naverBaseUrl)
            .filters(exchangeFilterFunctions -> {
                exchangeFilterFunctions.add(logRequest("naverWebClient"));
                exchangeFilterFunctions.add(logResponse("naverWebClient"));
            })
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("X-Naver-Client-Id", naverAppId)
            .defaultHeader("X-Naver-Client-Secret", naverAppSecret)
            .build();
    }

    @Bean
    @Qualifier("apiWebClient")
    public WebClient apiWebClient() {
        return WebClient.builder()
            .baseUrl(apiBaseUrl)
            .filters(exchangeFilterFunctions -> {
                exchangeFilterFunctions.add(logRequest("apiWebClient"));
                exchangeFilterFunctions.add(logResponse("apiWebClient"));
            })
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    private ExchangeFilterFunction logRequest(String serviceName) {

        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            StringBuilder sb = new StringBuilder("[WebClientService : Request][" + serviceName + "] \n");
            sb.append("[url : ").append(clientRequest.url()).append("]\n");
            log.debug(sb.toString());
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse(String serviceName) {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            StringBuilder sb = new StringBuilder("[WebClientService : Response][" + serviceName + "] ");
            sb.append("[status : ").append(clientResponse.statusCode()).append("] ");
            log.debug(sb.toString());

            if(clientResponse.statusCode().is4xxClientError()){
                log.warn("[Check Authorization Info]");
            }

            if(clientResponse.statusCode().is5xxServerError()){
                log.warn("[External Server Error]");
            }

            return Mono.just(clientResponse);
        });
    }

}
