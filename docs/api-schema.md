# Dokumentasi skema API

## Common Response Payload
- `400 Bad Request` karena invalid request payload
  ```json
  {
    "title": "Invalid field.",
    "details": {
      "field1": [
        "field1 tidak valid karena foo",
        "field1 tidak valid karena bar"
      ],
      "field2": [
        "field2 tidak valid karena foo"
      ]
    }
  }
  ```

## Daftar Endpoint

### Auth Register
`POST /auth/register`

Request payload
```json
{
  "username": "ivan",
  "password": "Ivan123!",
  "email": "ivan@example.com",
  "firstName": "Ivan",
  "lastName": "Rizkyanto",
  "profilePicture": "data:image/jpeg;base64,/9j/4QDeRXh...",
  "role": "USER"
}
```

Note: `profilePicture` menggunakan format [Data URI](https://developer.mozilla.org/en-US/docs/Web/URI/Reference/Schemes/data), seperti sebagai berikut:
```text
data:<mime-type>;base64,<base64-encoded-data>
```

Response
- Success `200 OK`
  ```json
  {
    "id": "09bfc897-8af8-4484-abec-3b215a61445e",
    "username": "ivan",
    "email": "ivan@example.com",
    "firstName": "Ivan",
    "lastName": "Rizkyanto",
    "profilePicture": "b810ebad-c761-4379-bf0a-b5b146750625.jpeg",
    "role": "USER",
    "permissions": null
  }
  ```

### Auth Login
`POST /auth/login`

Request payload
```json
{
  "username": "user",
  "password": "User123!"
}
```
Response
- Success `200 OK`
    ```json
    {
      "token": "eyJhbGciOiJSUzI1NiJ9.eyJzd..."
    }
    ```
- Wrong credentials `401 Unauthorized`

### Auth Me
`GET /auth/me`

Request header
```text
Authorization: Bearer <jwt-token>
```

Response
- Success `200 OK`
    ```json
    {
      "id": "84d591ca-c0e3-4716-8d5f-18d1c113cb51",
      "username": "user",
      "email": "user@example.com",
      "firstName": "User",
      "lastName": null,
      "profilePicture": "0cd7fd22-39cb-47a3-b80e-1020a52007d8.jpeg",
      "role": "USER",
      "permissions": [
        "UPDATE_PROFILE"
      ]
    }
    ```

### Auth Update Me
`PUT /auth/me`

Role yang diizinkan:
- `ADMIN`
- `USER` dengan permission `UPDATE_PROFILE`

Request header
```text
Authorization: Bearer <jwt-token>
```

Request payload
```json
{
  "username": "admin",
  "email": "admin@example.com",
  "firstName": "Admin",
  "lastName": null
}
```

Response
- Success `200 OK`
    ```json
    {
      "id": "84d591ca-c0e3-4716-8d5f-18d1c113cb51",
      "username": "user",
      "email": "user@example.com",
      "firstName": "User",
      "lastName": null,
      "profilePicture": "0cd7fd22-39cb-47a3-b80e-1020a52007d8.jpeg",
      "role": "USER",
      "permissions": [
        "UPDATE_PROFILE"
      ]
    }
    ```

### Auth Update Profile Picture
`PUT /auth/me`

Role yang diizinkan:
- `ADMIN`
- `USER` dengan permission `UPLOAD_PROFILE_PICTURE`

Request header
```text
Authorization: Bearer <jwt-token>
```

Request payload
```json
{
  "profilePicture": "data:image/jpeg;base64,/9j/4QDeRXh..."
}
```

Note: `profilePicture` menggunakan format [Data URI](https://developer.mozilla.org/en-US/docs/Web/URI/Reference/Schemes/data), seperti sebagai berikut:
```text
data:<mime-type>;base64,<base64-encoded-data>
```

Response
- Success `200 OK`
    ```json
    {
      "id": "84d591ca-c0e3-4716-8d5f-18d1c113cb51",
      "username": "user",
      "email": "user@example.com",
      "firstName": "User",
      "lastName": null,
      "profilePicture": "0cd7fd22-39cb-47a3-b80e-1020a52007d8.jpeg",
      "role": "USER",
      "permissions": [
        "UPDATE_PROFILE"
      ]
    }
    ```  

### Auth Update Password
`POST /auth/me/update-password`

Role yang diizinkan:
- `ADMIN`
- `USER` dengan permission `UPDATE_PASSWORD`

Request header
```text
Authorization: Bearer <jwt-token>
```

Request payload
```json
{
  "currentPassword": "User123!",
  "newPassword": "User456!"
}
```

Response
- Success `200 OK`

### Get User by Id
`GET /users/{userId}`

Role yang diizinkan:
- `ADMIN`

Request header
```text
Authorization: Bearer <jwt-token>
```

Response
- Success `200 OK`
    ```json
    {
      "id": "84d591ca-c0e3-4716-8d5f-18d1c113cb51",
      "username": "user",
      "email": "user@example.com",
      "firstName": "User",
      "lastName": null,
      "profilePicture": "0cd7fd22-39cb-47a3-b80e-1020a52007d8.jpeg",
      "role": "USER",
      "permissions": [
        "UPDATE_PROFILE"
      ]
    }
    ```  

### Update User Permissions by Id
`POST /users/{userId}/permissions`

Role yang diizinkan:
- `ADMIN`

Request header
```text
Authorization: Bearer <jwt-token>
```

Request payload
```json
{
  "permissions": [
    "UPDATE_PROFILE"
  ]
}
```

Response
- Success `200 OK`
    ```json
    {
      "id": "84d591ca-c0e3-4716-8d5f-18d1c113cb51",
      "username": "user",
      "email": "user@example.com",
      "firstName": "User",
      "lastName": null,
      "profilePicture": "0cd7fd22-39cb-47a3-b80e-1020a52007d8.jpeg",
      "role": "USER",
      "permissions": [
        "UPDATE_PROFILE"
      ]
    }
    ```  
  
### Get Uploaded Static File
`GET /uploads/{filename}`
