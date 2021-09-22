package com.mb.service1.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AES256Test {

    private static AES256 aes256;

    @BeforeAll
    public static void setup() {
        aes256 = new AES256();
        aes256.secretKey = "test_key";
        aes256.salt = "test_salt";
    }

    @Test
    public void testEncrypt() {
        String expected = "zaXeKlO+TLWgfp0oD6vS3A==";
        String actual = aes256.encrypt("test_message");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testDecrypt() {
        String expected = "test_message";
        String actual = aes256.decrypt("zaXeKlO+TLWgfp0oD6vS3A==");
        Assertions.assertEquals(expected, actual);
    }

}
