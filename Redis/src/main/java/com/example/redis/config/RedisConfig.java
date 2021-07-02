package com.example.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     *
     * 캐시의 TTL 적용 및 임의시점의 캐시등록/삭제 처리 등의 기능을 사용하고 싶으면 별도의 Configuration 설정필요
     * Springboot에서 제공하는 CachingConfigure 인터페이스를 구현해 놓은 거의 추상클래스인 CachingConfigureSupport클래스를 상속받아 별도의 Configuration Class 파일 추가
     * cacheManager() 을 오버라이딩  -> caheManager에 필요한 속성값 설정 (Serializer, TTL, keyPrefix 등)
     * @Cacheabe,CachePut,CacheEvice 어노테이션을 사용할때 cacheManager를 지정 또는, 필요한 클래스에 cacheManager를 주입받아 인라인 방식으로 직접 사용하면 된다.
     *
     */

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
