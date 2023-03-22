package com.mspark.blogsearch.api.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author : altntaos@gmail.com
 * @Date : 2023/03/21
 * @Time : 10:49
 */
@Configuration
public class RedisConfig {

//    @Bean
//    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory){
//        RedisSerializer<String> serializer = new StringRedisSerializer();
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(String.class);
//        RedisSerializationContext serializationContext =
//            RedisSerializationContext.<String, String>newSerializationContext()
//                .key(serializer)
//                .value(jackson2JsonRedisSerializer)
//                .hashKey(serializer)
//                .hashValue(jackson2JsonRedisSerializer)
//                .build();
//        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
//    }
}
