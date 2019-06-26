package com.test.payments.sharded.router;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// стащено в просторов гитхаба
public class MD5Hash implements RouteFunction {
    private MessageDigest instance;

    public MD5Hash() {
        try {
            instance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Cant create md5 hash function:", e);
        }
    }

    /**
     * понятия не имею, что происходит
     * @param key
     * @return
     */
    @Override
    public Long getValue(String key) {
        instance.reset();
        instance.update(key.getBytes());
        byte[] digest = instance.digest();

        long h = 0;
        for (int i = 0; i < 4; i++) {
            h <<= 8;
            h |= ((int) digest[i]) & 0xFF;
        }
        return h;
    }
}
