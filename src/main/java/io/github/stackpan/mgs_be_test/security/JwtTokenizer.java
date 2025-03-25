package io.github.stackpan.mgs_be_test.security;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import io.github.stackpan.mgs_be_test.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenizer {

    private final JwtEncoder encoder;

    @Value("${jwt.issuer:self}")
    private String jwtClaimsIssuer;

    @Value("${jwt.audience:self}")
    private String jwtClaimsAudience;

    @Value("${jwt.expiration:3600}")
    private Long jwtClaimsExpiration;

    public String generate(Authentication authenticated) {
        var now = Instant.now();
        var scope = authenticated.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer(jwtClaimsIssuer)
                .subject(((User) authenticated.getPrincipal()).getId().toString())
                .audience(List.of(jwtClaimsAudience))
                .issuedAt(now)
                .notBefore(now)
                .expiresAt(now.plusSeconds(jwtClaimsExpiration))
                .claim("scope", scope)
                .build();
        
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
