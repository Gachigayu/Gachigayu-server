package team.a5.gachigayu.security.token;

import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Getter
@Setter
@ConfigurationProperties("token")
@Component
public class TokenProperties {

    private String issuer;
    private String secretKey;

    public SecretKey getSigningKey() {
        byte[] encodedKey = Base64.getEncoder().encode(secretKey.getBytes());
        return Keys.hmacShaKeyFor(encodedKey);
    }
}
