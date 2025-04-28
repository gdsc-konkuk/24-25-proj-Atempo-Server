package juton113.Atempo.service;

import juton113.Atempo.domain.enums.ErrorCode;
import juton113.Atempo.exception.CustomException;
import juton113.Atempo.security.jwt.TokenService;
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

    public void saveRefreshToken(String key, String refreshToken) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, refreshToken, tokenService.getRefreshTokenTime(), TimeUnit.MILLISECONDS);
    }

    public void saveBlacklistedAccessToken(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        long expiration = tokenService.getExpiration(key);
        valueOperations.set(key, value, expiration, TimeUnit.MILLISECONDS);
    }

    public void validateStoredToken(String key, String token) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String foundToken = Optional.ofNullable(valueOperations.get(key)).orElseThrow(()-> new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if(!foundToken.equals(token))
            throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }

    public void deleteToken(String key) {
        if (!redisTemplate.delete(key))
            throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
