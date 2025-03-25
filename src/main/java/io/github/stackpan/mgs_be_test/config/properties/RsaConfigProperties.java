package io.github.stackpan.mgs_be_test.config.properties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rsa")
public record RsaConfigProperties(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
}
