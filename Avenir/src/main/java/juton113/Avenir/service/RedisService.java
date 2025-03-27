package juton113.Avenir.service;

import juton113.Avenir.domain.enums.ErrorCode;
import juton113.Avenir.exception.CustomException;
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
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, refreshToken, tokenService.getRefreshTokenTime(), TimeUnit.MILLISECONDS);
    }

        public void validateStoredToken(String key, String token) {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

            String foundToken = Optional.of(valueOperations.get(key)).orElseThrow(()-> new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

            if(!foundToken.equals(token))
                throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
        }

        public void deleteToken(String key) {
        // TODO: 검증 후 삭제할 수 있도록
        redisTemplate.delete(key);
    }
}
