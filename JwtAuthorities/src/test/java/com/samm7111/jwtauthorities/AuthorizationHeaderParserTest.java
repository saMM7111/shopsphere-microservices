package com.samm7111.jwtauthorities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class AuthorizationHeaderParserTest {

    @Test
    void extractsBearerToken() {
        assertEquals("abc.def.ghi", AuthorizationHeaderParser.extractToken("Bearer abc.def.ghi"));
    }

    @Test
    void returnsNullForInvalidHeader() {
        assertNull(AuthorizationHeaderParser.extractToken("Token x"));
    }
}