package com.springsecurity.template.service;

import com.springsecurity.template.security.record.JwtUser;
import com.springsecurity.template.security.CustomUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder encoder;

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication) {
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        CustomUser customUser = (CustomUser) authentication.getPrincipal();

        JwtUser jwtUser = new JwtUser(
                customUser.getFullName(),
                authentication.getName(),
                customUser.getEmailAddress(),
                customUser.getCountry(),
                customUser.isAccountConfirmed(),
                customUser.isAccountLocked(),
                authorities
        );

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(authentication.getName())
                .issuedAt(now)
                .expiresAt(now.plus(1L, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("authorities", authorities)
                .claim("user", jwtUser)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}