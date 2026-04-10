package com.samm7111.jwtauthorities;

public final class AuthorizationHeaderParser {

    private AuthorizationHeaderParser() {
    }

    public static String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring("Bearer ".length());
    }
}