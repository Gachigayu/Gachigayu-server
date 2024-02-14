package team.a5.gachigayu.security.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import team.a5.gachigayu.domain.User;
import team.a5.gachigayu.repository.UserRepository;

import java.time.Duration;
import java.util.Date;

@Component
public class TokenProvider {

    private final TokenProperties tokenProperties;
    private final UserRepository userRepository;

    public TokenProvider(TokenProperties tokenProperties, UserRepository userRepository) {
        this.tokenProperties = tokenProperties;
        this.userRepository = userRepository;
    }

    public String issueAccessToken(String email) {
        return generateJWT(email, Duration.ofHours(2));
    }

    public String issueRefreshToken(String email) {
        return generateJWT(email, Duration.ofDays(14));
    }

    private String generateJWT(String subject, Duration validityDuration) {
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .issuer(tokenProperties.getIssuer())
                .issuedAt(new Date())
                .subject(subject)
                .expiration(getExpirationDate(validityDuration))
                .signWith(tokenProperties.getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    private Date getExpirationDate(Duration validityDuration) {
        Date now = new Date();
        return new Date(now.getTime() + validityDuration.toMillis());
    }

    public boolean validToken(String token) {
        try {
            parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        String email = parseSignedClaims(token)
                .getPayload()
                .getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return UsernamePasswordAuthenticationToken.authenticated(
                user, null, AuthorityUtils.createAuthorityList("ROLE_USER")
        );
    }

    private Jws<Claims> parseSignedClaims(String token) {
        return Jwts.parser()
                .verifyWith(tokenProperties.getSigningKey())
                .build()
                .parseSignedClaims(token);
    }
}
