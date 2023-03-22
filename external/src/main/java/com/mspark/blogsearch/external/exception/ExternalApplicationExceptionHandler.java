package com.mspark.blogsearch.external.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MissingRequestValueException;
import reactor.core.publisher.Mono;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 23:20
 */
@Slf4j
@RestControllerAdvice
public class ExternalApplicationExceptionHandler {

    @ExceptionHandler(MissingRequestValueException.class)
    @ResponseBody
    public Mono<ResponseEntity> handleMissingRequestValueException(MissingRequestValueException e) {
        String errMessage = e.getMessage();
        log.warn("msg = {}", errMessage);

        return Mono.just(ResponseEntity.badRequest().body(errMessage));
    }

    @ExceptionHandler(InvalidSearchQueryParamException.class)
    @ResponseBody
    public Mono<ResponseEntity> handleInvalidSearchQueryParamException(InvalidSearchQueryParamException e) {
        String errMessage = e.getMessage();
        log.warn("msg = {}", errMessage);

        return Mono.just(ResponseEntity.badRequest().body(errMessage));
    }

    @ExceptionHandler(ExternalException.class)
    @ResponseBody
    public Mono<ResponseEntity> handleExternalException(ExternalException e) {
        String errMessage = e.getMessage();
        log.warn("msg = {}", errMessage);

        return Mono.just(ResponseEntity.internalServerError().body(errMessage));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Mono<ResponseEntity> handleException(Exception e) {
        String errMessage = e.getMessage();
        log.warn("msg = {}", errMessage);

        return Mono.just(ResponseEntity.internalServerError().body(errMessage));
    }

}
