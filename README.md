# How to Run Instructions

## Prerequisite
- Java 21 or newer
- Maven
- PostgreSQL

## Setup
-  Configure database url connection in `src/main/resources/application.properties`
    ```properties
    spring.datasource.url=jdbc:postgresql://${POSTGRES_HOST:localhost}:${POSTGRES_PORT:5432}/${POSTGRES_DB:app}
    spring.datasource.username=${POSTGRES_USER:postgres}
    spring.datasource.password=${POSTGRES_PASSWORD}
    ```
- Generate private and public RSA keypairs in the current directory using OpenSSL or others. Or just use paste these:
  - `private.pem`
     ```text
     -----BEGIN PRIVATE KEY-----
     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
     woFTuuq88GuP/mJxWNBrwv0veosnhYM=
     -----END PRIVATE KEY-----
     ```

  - `public.pem`
     ```text
     -----BEGIN PUBLIC KEY-----
     MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEApGYmfylyMHCzTajtoEhh
     nnfhcZm0BBeCt7IUFhllfPOFRLCt4EbZF9vAOcGQ5UzWNydDxga40EEHGlZexHuU
     42EhL5mlpCvYD9VvbwOSDY9sOw1CuFH5FJwu1NvlU5X+xjz5R0B1sbie5b6Goxml
     kx5sEFuOY1QolCl0oVfiF3RaIaUxzZYIUeCCFuuDPLzcCC1XCt02QEy2pVlX+YTO
     UK0t1YqtMYdGQEGDsMW6F9REGPcDliQv9TQNk7n/hiqC6THGuCr4L3ql7qWf8wSW
     zaa5d01okVhzORC9+hzMhbXMbJjPAKyBZ+ZnaFE6MlRsTN2E2OOmAsMc8Dm3Vb1A
     bFqWAp1EFevhXPzeQOwRct+eKq2034+mc/wXuDh7USNeiI8OEwY+CdXGDX/RJfXh
     R/HrDAiqUbUwuDTXCMdc6QHqd4Bo9JNRFKFIUVzRcCTk5xbnYn27RPfRorZl2dxW
     s+Ub7YuyXB2gMFRhuTwsGiJBLDvp2impGQWCB+x1r9gZCwtlE+bJIg8aIdSJzlzJ
     mc2MZHOi1j8rvJZhKiv4kmUg853gTC+F060LY04JF4y4/zLtTb+FmLwjAJRqaV1a
     TreeWvvJZeBV+HMd3BTVml+6TkeW1i+S387KdtYCns2bOm8mBMtfURe9ShOJWNlU
     AuR1KaYjXxw2DTBALzfSxVUCAwEAAQ==
     -----END PUBLIC KEY-----
     ```

## Running
```shell
./mvnw spring-boot:run
```

# API Docs

You can refer to this [README](https://github.com/stackpan/mgs-be-test/blob/main/docs/api-schema.md)