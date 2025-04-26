package juton113.Atempo.security.oauth;

import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private final String provider;
    private final String providerId;
    private final String name;
    private final String email;
    private final String picture;

    private OAuthAttributes(String provider, String providerId, String name, String email, String picture) {
        this.provider = provider;
        this.providerId = providerId;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String provider, Map<String, Object> attributes) throws RuntimeException {
        switch (provider) {
            case "google":
                return new OAuthAttributes(
                        provider,
                        (String) attributes.get("sub"),
                        (String) attributes.get("name"),
                        (String) attributes.get("email"),
                        (String) attributes.get("picture")
                );
            default:
                throw new RuntimeException("지원하지 않는 OAuth Provider: " + provider);
        }
    }
}
