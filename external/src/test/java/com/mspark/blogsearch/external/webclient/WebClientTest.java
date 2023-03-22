package com.mspark.blogsearch.external.webclient;

import java.io.IOException;
import java.time.Duration;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.retry.Retry;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 8:34
 */
public class WebClientTest {

    @Test
    @DisplayName("블로그 검색 외부 API 에러시 3번 재시도 후 다른 API를 호출한다")
    public void test_webclient_retry_exhausted_then_call_others() throws IOException {
        //Given
        MockWebServer mockWebServerOne = new MockWebServer();
        MockWebServer mockWebServerAnother = new MockWebServer();

        String successBody = "OK";

        mockWebServerOne.enqueue(new MockResponse().setResponseCode(400));
        mockWebServerOne.enqueue(new MockResponse().setResponseCode(400));
        mockWebServerOne.enqueue(new MockResponse().setResponseCode(400));
        mockWebServerOne.enqueue(new MockResponse().setResponseCode(400));

        mockWebServerAnother.enqueue(new MockResponse().setResponseCode(200).setBody(successBody));

        mockWebServerOne.start();
        mockWebServerAnother.start();

        HttpUrl mockUrlOne = mockWebServerOne.url("/test/k");
        HttpUrl mockUrlAnother = mockWebServerAnother.url("/test/n");

        Mono<String> response =
            WebClient.builder().build().get().uri(mockUrlOne.uri()).retrieve().bodyToMono(String.class).retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(t -> Exceptions.isRetryExhausted(t),
                    t -> WebClient.builder().build().get().uri(mockUrlAnother.uri()).retrieve().bodyToMono(String.class).retryWhen(Retry.backoff(3, Duration.ofSeconds(1))));

        StepVerifier.create(response)
                .expectNext(successBody).expectComplete().verify();

        mockWebServerOne.shutdown();
        mockWebServerAnother.shutdown();

    }
}
