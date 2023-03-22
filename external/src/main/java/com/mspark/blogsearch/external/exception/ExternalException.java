package com.mspark.blogsearch.external.exception;

import lombok.Getter;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/20
 * @Time : 23:16
 */
@Getter
abstract class ExternalException extends RuntimeException {

    abstract public String getMessage();
}
