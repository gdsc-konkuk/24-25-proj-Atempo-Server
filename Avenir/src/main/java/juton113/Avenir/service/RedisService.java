package juton113.Avenir.service;

import juton113.Avenir.security.jwt.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RedisService {
    private final TokenService tokenService;
    private final RedisTemplate<String, String> redisTemplate;

    public void saveToken(String key, String refreshToken) {
        // TODO: 중복 검사를 적용할 지 고려할 것
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, refreshToken, tokenService.getRefreshTokenTime(), TimeUnit.MILLISECONDS);
    }

    public Optional<String> getToken(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return Optional.ofNullable(valueOperations.get(key));
    }

    public void deleteToken(String key) {
        // TODO: 검증 후 삭제할 수 있도록
        redisTemplate.delete(key);
    }
}
